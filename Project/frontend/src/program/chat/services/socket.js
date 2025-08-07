// // chat/services/socket.js

// let socket;

// export const connectSocket = (userId, onMessage) => {
//   socket = new WebSocket(`ws://localhost:8080/ws/chat/${userId}`);

//   socket.onopen = () => {
//     console.log("WebSocket 연결 성공");
//   };

//   socket.onmessage = (event) => {
//     const message = JSON.parse(event.data);
//     onMessage(message); // 콜백 실행
//   };

//   socket.onclose = () => {
//     console.log("WebSocket 연결 종료");
//   };

//   socket.onerror = (error) => {
//     console.error("WebSocket 에러", error);
//   };
// };

// export const sendMessage = (messageObj) => {
//   if (socket && socket.readyState === WebSocket.OPEN) {
//     socket.send(JSON.stringify(messageObj));
//   }
// };

// export const disconnectSocket = () => {
//   if (socket) socket.close();
// };

// import { Client } from '@stomp/stompjs';
// import SockJS from 'sockjs-client';

// const SOCKET_URL = 'http://localhost:8080/ws'; // 백엔드 주소, 필요시 변경

// let stompClient = null;

// export const connectChatSocket = (manageNum, onMessageReceived) => {
//   const socket = new SockJS(SOCKET_URL);
//   stompClient = new Client({
//     webSocketFactory: () => socket,
//     debug: (str) => {
//       console.log('[STOMP]', str);
//     },
//     reconnectDelay: 5000,
//     onConnect: () => {
//       console.log('STOMP 연결 성공');

//       stompClient.subscribe(`/topic/user/${manageNum}`, (message) => {
//         if (message.body) {
//           const parsed = JSON.parse(message.body);
//           onMessageReceived(parsed);
//         }
//       });
//     },
//     onStompError: (frame) => {
//       console.error('STOMP 오류:', frame.headers['message']);
//       console.error('디테일:', frame.body);
//     },
//   });

//   stompClient.activate();
// };

// export const sendMessage = (manageNum, chatCont) => {
//   if (stompClient && stompClient.connected) {
//     const message = {
//       manageNum,
//       chatCont,
//       senderType: 'USER', // 로그인 정보에 따라 ADMIN 등으로 변경 가능
//     };
//     stompClient.publish({
//       destination: '/app/chat/send',
//       body: JSON.stringify(message),
//     });
//   } else {
//     console.error('STOMP 클라이언트가 연결되어 있지 않습니다.');
//   }
// };

// export const disconnectChatSocket = () => {
//   if (stompClient) {
//     stompClient.deactivate();
//     console.log('STOMP 연결 해제');
//   }
// };

// src/program/chat/services/socket.js

export const connectChatSocket = (manageNum, onMessage) => {
  console.log('connectChatSocket called', manageNum);
  // 테스트용, 1초 후 가짜 메시지 콜백 호출
  setTimeout(() => {
    onMessage({ senderType: 'USER', chatCont: '테스트 메시지입니다.' });
  }, 1000);
};

export const sendMessage = (manageNum, message) => {
  console.log('sendMessage called', manageNum, message);
};

export const disconnectChatSocket = () => {
  console.log('disconnectChatSocket called');
};
