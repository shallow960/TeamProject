// services/socketService.js
let socket = null;

export const connectSocket = (userId) => {
  socket = new WebSocket(`ws://localhost:8080/ws/chat?user=${userId}`);

  socket.onopen = () => console.log('WebSocket connected');
  socket.onclose = () => console.log('WebSocket closed');
};

export const sendMessage = (messageObj) => {
  if (socket && socket.readyState === WebSocket.OPEN) {
    socket.send(JSON.stringify(messageObj));
  }
};

export const onMessage = (callback) => {
  if (socket) {
    socket.onmessage = (event) => {
      const data = JSON.parse(event.data);
      callback(data);
    };
  }
};

export const disconnectSocket = () => {
  if (socket) {
    socket.close();
  }
};
