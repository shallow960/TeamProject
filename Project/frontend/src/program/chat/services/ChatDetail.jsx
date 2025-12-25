import React, { useEffect, useState, useRef } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../../../common/api/axios.js"; // default import로 변경
import ChatService from "./ChatService";
import "../style/Chat.css";

// JWT 토큰 디코딩 함수 (payload만 가져옴)
const decodeJwt = (token) => {
  try {
    const payload = token.split(".")[1];
    const decoded = atob(payload);
    return JSON.parse(decoded);
  } catch (e) {
    return null;
  }
};

// 상세 페이지 내부용 ChatInput (이 페이지에서만 사용)
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
        className="ui-input"
        placeholder="메시지를 입력하세요..."
        value={message}
        onChange={handleMessageChange}
        disabled={isSending}
      />
      <div className="temp_btn md">
        <button
          type="submit"
          className="btn"
          disabled={isSending || !message.trim()}
        >
          {isSending ? "전송 중..." : "전송"}
        </button>
      </div>
    </form>
  );
};

const ChatDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const chatRoomNum = id;
  const [messages, setMessages] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const chatMessagesRef = useRef(null);
  const [userRole, setUserRole] = useState(null);

  const roleDisplayNames = {
    ADMIN: "관리자",
    MEMBER: "회원",
    USER: "회원",
  };

  const formatTime = (isoString) => {
    if (!isoString) return "";
    const date = new Date(isoString);
    return date.toLocaleTimeString("ko-KR", {
      hour: "2-digit",
      minute: "2-digit",
    });
  };

  const fetchChatHistory = async () => {
    setLoading(true);
    try {
      const res = await api.get(`/chat/detail/${chatRoomNum}`);
      setMessages(res.data);
      setError(null);
    } catch (err) {
      console.error("채팅 기록 조회 실패:", err);
      setError("채팅 기록을 불러올 수 없습니다. 권한을 확인해주세요.");
      setMessages([]);
    } finally {
      setLoading(false);
    }
  };

  // 1) 페이지 진입 시 로그인 여부 체크 + 채팅 기록 로딩 + 사용자 role 설정
  useEffect(() => {
    const token = localStorage.getItem("accessToken");
    if (!token) {
      alert("채팅은 로그인 후 이용 가능합니다.");
      navigate("/login", { replace: true });
      return;
    }

    fetchChatHistory();

    const decodedToken = decodeJwt(token);
    if (decodedToken && decodedToken.auth) {
      setUserRole(decodedToken.auth);
    } else {
      setUserRole("MEMBER");
    }
  }, [chatRoomNum, navigate]);

  // 2) WebSocket 연결 및 구독
  useEffect(() => {
    if (!chatRoomNum) return;

    const token = localStorage.getItem("accessToken");
    if (!token) {
      // 여기까지 올 일은 거의 없지만, 방어용
      return;
    }

    ChatService.connect(
      token,
      () => {
        console.log("WebSocket 연결 성공");
        ChatService.subscribe(chatRoomNum, (message) => {
          setMessages((prev) => [...prev, message]);
        });
      },
      (err) => {
        console.error("WebSocket 연결 실패:", err);
      }
    );

    return () => {
      ChatService.disconnect();
    };
  }, [chatRoomNum]);

  // 3) 메시지 변경 시 스크롤 맨 아래로 이동
  useEffect(() => {
    if (chatMessagesRef.current) {
      chatMessagesRef.current.scrollTop = chatMessagesRef.current.scrollHeight;
    }
  }, [messages]);

  if (loading) return <div>로딩 중...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div>
      <h3>채팅방 {chatRoomNum} 대화 내역</h3>
      <div
        ref={chatMessagesRef}
        className="box"
        style={{ height: "400px", overflowY: "auto" }}
      >
        {messages.length > 0 ? (
          messages.map((msg, index) => (
            <div
              key={index}
              className={`message-item ${
                msg.senderRole === userRole ? "my-message" : "other-message"
              }`}
            >
              <div className="message-content-wrapper">
                <strong>
                  {roleDisplayNames[msg.senderRole] || msg.senderRole}
                </strong>
                : {msg.message}
                <span className="message-timestamp">
                  {formatTime(msg.timestamp)}
                </span>
              </div>
            </div>
          ))
        ) : (
          <div>대화 기록이 없습니다.</div>
        )}
      </div>

      <div className="temp_input">
        <ChatInput chatRoomNum={chatRoomNum} />
      </div>

      <div className="temp_btn white md">
        <button type="button" className="btn" onClick={() => navigate(-1)}>
          목록보기
        </button>
      </div>
    </div>
  );
};

export default ChatDetail;
