import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const SOCKET_URL = 'http://localhost:8080/ws'; // 백엔드 WebSocket 엔드포인트

let stompClient = null;

export const connectChatSocket = (manageNum, onMessageReceived) => {
  const socket = new SockJS(SOCKET_URL);
  stompClient = new Client({
    webSocketFactory: () => socket,
    debug: function (str) {
      console.log(str);
    },
    reconnectDelay: 5000,
    onConnect: () => {
      console.log('STOMP 연결됨');

      // 서버가 보내는 메시지 구독
      stompClient.subscribe(`/topic/user/${manageNum}`, (message) => {
        if (message.body) {
          const parsedMsg = JSON.parse(message.body);
          onMessageReceived(parsedMsg);
        }
      });
    },
    onStompError: (frame) => {
      console.error('STOMP 오류:', frame.headers['message']);
      console.error('디테일:', frame.body);
    },
  });

  stompClient.activate();
};

export const sendMessage = (manageNum, chatCont) => {
  if (stompClient && stompClient.connected) {
    const message = {
      manageNum,
      chatCont,
      senderType: 'USER', // 필요에 따라 ADMIN 등으로 변경
    };
    stompClient.publish({
      destination: '/app/chat/send',
      body: JSON.stringify(message),
    });
  } else {
    console.error('STOMP 클라이언트가 연결되어 있지 않습니다.');
  }
};

export const disconnectChatSocket = () => {
  if (stompClient) {
    stompClient.deactivate();
    console.log('STOMP 연결 종료');
  }
};
