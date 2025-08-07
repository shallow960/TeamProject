// chat/services/chatApi.js

import axios from "axios";

const BASE_URL = "/api/chat";

// 채팅 내역 불러오기
export const getChatHistory = async (userId) => {
  const res = await axios.get(`${BASE_URL}/history/${userId}`);
  return res.data;
};

// 메시지 전송
export const sendChatMessage = async (messageDto) => {
  const res = await axios.post(`${BASE_URL}/send`, messageDto);
  return res.data;
};
