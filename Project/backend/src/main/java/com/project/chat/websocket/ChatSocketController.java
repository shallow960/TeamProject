package com.project.chat.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.project.chat.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {

    private final ChatService chatService;

    // 클라이언트가 "/app/chat/send"로 메시지를 보낼 경우 이 메서드로 전달됨
    @MessageMapping("/chat/send")
    @SendTo("/topic/public") // 메시지를 "/topic/public"을 구독한 모든 클라이언트에게 브로드캐스트
    public ChatMessage sendMessage(ChatMessage message) {
        // DB에 저장 (chatService에서 구현 필요)
        chatService.saveChatViaSocket(message);

        // 그대로 브로드캐스트
        return message;
    }
}
