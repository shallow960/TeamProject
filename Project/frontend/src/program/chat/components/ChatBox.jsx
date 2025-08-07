// ✅ 올바른 ChatBox.jsx 예시
import React from 'react';
import { chatStyles } from '../style/ChatTailwindStyles';

const ChatBox = () => {
  return (
    <div className={chatStyles.container}>
      {/* 채팅 내용 */}
      <p>채팅 박스입니다</p>
    </div>
  );
};

export default ChatBox;
