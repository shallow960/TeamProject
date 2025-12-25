import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

let stompClient = null;

const ChatService = {
  /**
   * WebSocket 및 STOMP 연결을 시작합니다.
   * @param {string} token - JWT 토큰
   * @param {function} onConnectCallback - 연결 성공 시 실행될 콜백 함수
   * @param {function} onErrorCallback - 연결 실패 시 실행될 콜백 함수
   */
  connect: (token, onConnectCallback, onErrorCallback) => {
    // 0) 토큰 없으면 연결 시도 자체를 하지 않음
    if (!token) {
      console.warn("채팅은 로그인 후에만 이용 가능합니다. (토큰 없음)");
      if (onErrorCallback) {
        onErrorCallback(new Error("NO_TOKEN"));
      }
      return;
    }

    // 이미 연결되어 있으면 재사용
    if (stompClient && stompClient.connected) {
      console.log("STOMP 클라이언트가 이미 연결되어 있습니다.");
      if (onConnectCallback) onConnectCallback();
      return;
    }

    // SockJS를 사용하여 WebSocket 연결
    stompClient = Stomp.over(() => {
      return new SockJS("/api/ws", null, {
        transports: ["websocket", "xhr-streaming", "xhr-polling"],
      });
      // return new SockJS("http://152.67.212.81:8090/ws");
    });

    // 필요하면 디버그 끄기
    // stompClient.debug = () => {};

    const headers = {
      Authorization: `Bearer ${token}`,
    };

    stompClient.connect(
      headers,
      (frame) => {
        console.log("STOMP 연결 성공:", frame);
        if (onConnectCallback) onConnectCallback();
      },
      (error) => {
        console.error("STOMP 연결 실패:", error);
        if (onErrorCallback) onErrorCallback(error);
      }
    );
  },

  /**
   * 특정 채팅방을 구독하여 실시간 메시지를 수신합니다.
   */
  subscribe: (chatRoomNum, onMessageReceived) => {
    if (!stompClient || !stompClient.connected) {
      console.error("STOMP 클라이언트가 연결되지 않았습니다.");
      return;
    }
    const destination = `/sub/chat/detail/${chatRoomNum}`;
    stompClient.subscribe(destination, (message) => {
      onMessageReceived(JSON.parse(message.body));
    });
  },

  /**
   * 메시지를 서버로 전송합니다.
   */
  sendMessage: (chatDto) => {
    if (!stompClient || !stompClient.connected) {
      console.error("STOMP 클라이언트가 연결되지 않았습니다.");
      return;
    }
    const destination = "/pub/chat/message";
    stompClient.send(destination, {}, JSON.stringify(chatDto));
  },

  /**
   * STOMP 연결 종료
   */
  disconnect: () => {
    if (stompClient) {
      stompClient.disconnect(() => {
        console.log("STOMP 클라이언트 연결 종료");
      });
    }
  },
};

export default ChatService;
