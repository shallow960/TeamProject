// services/messageHandler.js

export const formatMessage = (msg) => {
  return {
    ...msg,
    time: new Date(msg.timestamp).toLocaleTimeString(),
  };
};

export const isFromCurrentUser = (msg, userId) => {
  return msg.senderId === userId;
};
