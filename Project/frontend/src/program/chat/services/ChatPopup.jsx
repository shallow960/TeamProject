import React, { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import ChatService from "./ChatService";
import api from "../../../common/api/axios.js";
import "../style/ChatPopup.css";

// 팝업 내부용 ChatInput
const ChatInput = ({ chatRoomNum }) => {
  const [message, setMessage] = useState("");
  const [isSending, setIsSending] = useState(false);

  const handleMessageChange = (event) => {
    setMessage(event.target.value);
  };

  const handleSendMessage = async (event) => {
    event.preventDefault();
    const trimmedMessage = message.trim();

    if (!trimmedMessage || isSending) {
      console.log("메시지가 비어 있거나 이미 전송 중입니다.");
      return;
    }

    setIsSending(true);

    const chatDto = {
      chatRoomNum: chatRoomNum,
      message: trimmedMessage,
    };

    try {
      await ChatService.sendMessage(chatDto);
      setMessage("");
    } catch (error) {
      console.error("메시지 전송 실패:", error);
    } finally {
      setIsSending(false);
    }
  };

  return (
    <form className="chat-input-form" onSubmit={handleSendMessage}>
      <input
        type="text"
        className="chat-input-field"
        placeholder="메시지를 입력하세요..."
        value={message}
        onChange={handleMessageChange}
        disabled={isSending}
      />
      <button
        type="submit"
        className="chat-send-btn"
        disabled={isSending || !message.trim()}
      >
        전송
      </button>
    </form>
  );
};

// ChatPopup 컴포넌트
const ChatPopup = ({ onClose }) => {
  const [chatHistory, setChatHistory] = useState([]);
  const [chatRoomNum, setChatRoomNum] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const chatMessagesRef = useRef(null);
  const navigate = useNavigate();

  const formatTime = (isoString) => {
    if (!isoString) return "";
    const date = new Date(isoString);
    return date.toLocaleTimeString("ko-KR", {
      hour: "2-digit",
      minute: "2-digit",
    });
  };

  const handleAddMessage = (message) => {
    setChatHistory((prevHistory) => [...prevHistory, message]);
  };

  // 1. 컴포넌트 마운트 시 채팅방 초기화
  useEffect(() => {
    const initializeChat = async () => {
      try {
        const token = localStorage.getItem("accessToken");
        if (!token) {
          alert("채팅은 로그인 후 이용 가능합니다.");
          if (onClose) onClose();
          navigate("/login", { replace: true });
          return;
        }

        // 채팅방 생성/가져오기
        const response = await api.post("/chat/detail");
        const newChatRoomNum = response.data.chatRoomNum;
        setChatRoomNum(newChatRoomNum);

        // 기존 채팅 기록
        const historyResponse = await api.get(`/chat/detail/${newChatRoomNum}`);
        setChatHistory(historyResponse.data);
      } catch (e) {
        console.error("채팅 초기화 오류:", e);
        setError("채팅을 시작할 수 없습니다. 다시 시도해주세요.");
      } finally {
        setIsLoading(false);
      }
    };
    initializeChat();
  }, [navigate, onClose]);

  // 2. chatRoomNum이 유효해졌을 때 웹소켓 연결 + 구독
  useEffect(() => {
    if (!chatRoomNum) return;

    const token = localStorage.getItem("accessToken");
    if (!token) return;

    ChatService.connect(
      token,
      () => {
        console.log("WebSocket 연결 성공");
        ChatService.subscribe(chatRoomNum, (message) => {
          handleAddMessage(message);
        });
      },
      (err) => {
        console.error("WebSocket 연결 실패:", err);
        setError("채팅 서버에 연결할 수 없습니다.");
      }
    );

    return () => {
      ChatService.disconnect();
    };
  }, [chatRoomNum]);

  // 3. 메시지가 추가될 때마다 스크롤 맨 아래로
  useEffect(() => {
    if (chatMessagesRef.current) {
      chatMessagesRef.current.scrollTop = chatMessagesRef.current.scrollHeight;
    }
  }, [chatHistory]);

  return (
    <div className="chat-popup">
      <div className="chat-header">
        <span className="chat-title">그룹 채팅</span>
        <button className="close-btn" onClick={onClose}>
          &times;
        </button>
      </div>
      <div className="chat-messages" ref={chatMessagesRef}>
        {isLoading && (
          <p className="loading-message">채팅을 불러오는 중...</p>
        )}
        {error && <p className="error-message">{error}</p>}
        {!isLoading &&
          !error &&
          chatHistory.map((msg, index) => (
            <div
              key={index}
              className={`chat-line ${
                msg.senderRole === "MEMBER" || msg.senderRole === "USER"
                  ? "my-line"
                  : "other-line"
              }`}
            >
              <div
                className={`chat-message-bubble ${
                  msg.senderRole === "MEMBER" || msg.senderRole === "USER"
                    ? "my-bubble"
                    : "other-bubble"
                }`}
              >
                <span className="message-content">{msg.message}</span>
              </div>
              <span className="message-timestamp">
                {formatTime(msg.timestamp)}
              </span>
            </div>
          ))}
      </div>
      <div className="chat-input-container">
        {/* 에러 없고, 채팅방 번호 있을 때만 입력창 노출 */}
        {!isLoading && !error && chatRoomNum && (
          <ChatInput chatRoomNum={chatRoomNum} />
        )}
      </div>
    </div>
  );
};

export default ChatPopup;
