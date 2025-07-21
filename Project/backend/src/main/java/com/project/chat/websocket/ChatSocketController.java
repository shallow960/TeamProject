package com.project.chat.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.project.chat.dto.request.ChatSocketRequestDto;
import com.project.chat.dto.response.ChatSocketResponseDto;
import com.project.chat.entity.ChatEntity;
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
        // 저장
        ChatEntity saved = chatService.saveChatSocket(message);

        // DTO 변환
        ChatSocketResponseDto response = new ChatSocketResponseDto(saved);

        // 해당 manageNum을 구독 중인 사용자에게 전송
        messagingTemplate.convertAndSend("/topic/user/" + message.getManageNum(), response);
    }

}
