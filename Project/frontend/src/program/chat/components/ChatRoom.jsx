// import { useEffect, useState, useRef } from "react";
// import { connectChatSocket, disconnectChatSocket, sendMessage } from "../services/chatSocket";

// const ChatRoom = () => {
//   const [messages, setMessages] = useState([]); // 채팅 메시지 목록
//   const [inputMsg, setInputMsg] = useState(""); // 입력 중인 메시지

//   const wsRef = useRef(null); // WebSocket 인스턴스 참조

//     useEffect(() => {
//     const ws = connectChatSocket(onMessageReceive);
//     wsRef.current = ws;

//     return () => {
//       disconnectChatSocket(wsRef.current);
//     };
//   }, []);

//   const onMessageReceive = (msg) => {
//     const parsed = JSON.parse(msg);
//     setMessages((prev) => [...prev, parsed]);
//   };

//     const handleSend = () => {
//     if (!inputMsg.trim()) return;
//     const messageData = {
//       sender: "user1", // 임시값. 추후 로그인 정보에서 가져오기
//       content: inputMsg,
//       timestamp: new Date().toISOString(),
//     };
//     sendMessage(wsRef.current, JSON.stringify(messageData));
//     setInputMsg(""); // 입력창 비우기
//   };

//     return (
//     <div className="p-4 border rounded w-full max-w-2xl mx-auto">
//       <div className="h-96 overflow-y-auto border p-2 mb-2 bg-gray-100 rounded">
//         {messages.map((msg, idx) => (
//           <div key={idx} className="mb-1">
//             <strong>{msg.sender}:</strong> {msg.content}
//           </div>
//         ))}
//       </div>
//       <div className="flex gap-2">
//         <input
//           className="flex-1 p-2 border rounded"
//           value={inputMsg}
//           onChange={(e) => setInputMsg(e.target.value)}
//           onKeyDown={(e) => e.key === "Enter" && handleSend()}
//         />
//         <button className="px-4 py-2 bg-blue-500 text-white rounded" onClick={handleSend}>
//           전송
//         </button>
//       </div>
//     </div>
//   );
// // };// src/program/chat/components/ChatRoom.jsx
// import React, { useEffect, useState } from 'react';
// import { connectChatSocket, sendMessage, disconnectChatSocket } from '../services/socket';

// const ChatRoom = ({ manageNum }) => {
//   const [messages, setMessages] = useState([]);
//   const [inputMsg, setInputMsg] = useState('');

//   useEffect(() => {
//     connectChatSocket(manageNum, (message) => {
//       setMessages((prev) => [...prev, message]);
//     });

//     return () => {
//       disconnectChatSocket();
//     };
//   }, [manageNum]);

//   const handleSend = () => {
//     if (!inputMsg.trim()) return;
//     sendMessage(manageNum, inputMsg);
//     setInputMsg('');
//   };

//   return (
//     <div className="flex flex-col h-[500px] max-w-3xl mx-auto border rounded shadow">
//       {/* 메시지 출력 영역 */}
//       <div className="flex-1 p-4 overflow-y-auto bg-gray-100">
//         {messages.length === 0 && <p className="text-gray-500">아직 대화 내용이 없습니다.</p>}
//         {messages.map((msg, idx) => (
//           <div key={idx} className="mb-3">
//             <div className="text-sm text-gray-600">
//               <strong>{msg.senderType}</strong> <span className="ml-2 text-xs">{msg.sendTime}</span>
//             </div>
//             <div className="text-base">{msg.chatCont}</div>
//           </div>
//         ))}
//       </div>

//       {/* 입력 및 전송 영역 */}
//       <div className="p-2 flex border-t">
//         <input
//           type="text"
//           className="flex-1 border rounded px-3 py-2 mr-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
//           placeholder="메시지를 입력하세요"
//           value={inputMsg}
//           onChange={(e) => setInputMsg(e.target.value)}
//           onKeyDown={(e) => e.key === 'Enter' && handleSend()}
//         />
//         <button
//           className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
//           onClick={handleSend}
//         >
//           전송
//         </button>
//       </div>
//     </div>
//   );
// };

// export default ChatRoom;

// src/program/chat/components/ChatRoom.jsx

import React, { useState, useEffect } from 'react';
import { connectChatSocket, sendMessage, disconnectChatSocket } from '../services/socket';

const ChatRoom = ({ manageNum }) => {
  const [messages, setMessages] = useState([]);
  const [inputMsg, setInputMsg] = useState('');

  useEffect(() => {
    connectChatSocket(manageNum, (message) => {
      setMessages((prev) => [...prev, message]);
    });

    return () => {
      disconnectChatSocket();
    };
  }, [manageNum]);

  const handleSend = () => {
    if (!inputMsg.trim()) return;
    sendMessage(manageNum, inputMsg);
    setInputMsg('');
  };

  return (
    <div>
      <div style={{ height: '300px', overflowY: 'auto', border: '1px solid black' }}>
        {messages.map((msg, idx) => (
          <div key={idx}>
            <b>{msg.senderType}</b>: {msg.chatCont}
          </div>
        ))}
      </div>
      <input
        type="text"
        value={inputMsg}
        onChange={(e) => setInputMsg(e.target.value)}
        onKeyDown={(e) => e.key === 'Enter' && handleSend()}
      />
      <button onClick={handleSend}>전송</button>
    </div>
  );
};

export default ChatRoom;
