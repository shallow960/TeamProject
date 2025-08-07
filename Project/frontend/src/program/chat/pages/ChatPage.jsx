// // src/program/chat/pages/ChatPage.jsx
// import ChatBox from "../components/ChatBox";

// const ChatPage = () => {
//   return (
//     <div>
//       <h1>1:1 채팅</h1>
//       <ChatBox />
//     </div>
//   );
// };

// export default ChatPage;

// src/program/chat/pages/ChatPage.jsx// src/program/chat/pages/ChatPage.jsx

import React from 'react';
import ChatRoom from '../components/ChatRoom';

const ChatPage = () => {
  const manageNum = 1234; // 테스트용 임시 ID

  return (
    <div>
      <h1>채팅 페이지</h1>
      <ChatRoom manageNum={manageNum} />
    </div>
  );
};

export default ChatPage;
