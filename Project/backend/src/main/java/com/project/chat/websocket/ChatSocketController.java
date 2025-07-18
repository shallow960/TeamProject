package com.project.chat.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.project.chat.dto.ChatSocketRequestDto;
import com.project.chat.service.ChatService;

@Controller
public class ChatSocketController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatSocketController(ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat/send")
    public void sendMessage(ChatSocketRequestDto message) {
        // 1. 수신된 메시지를 DB에 저장
        chatService.saveChatSocket(message);

        // 2. 해당 사용자에게 메시지 전송 (구독 중인 클라이언트에게)
        messagingTemplate.convertAndSend("/topic/user/" + message.getManageNum(), message);
    }
}
