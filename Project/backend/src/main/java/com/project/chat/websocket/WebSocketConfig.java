package com.project.chat.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // 클라이언트가 접속할 WebSocket 엔드포인트
                .setAllowedOrigins("*") // 허용 도메인 설정 (CORS 허용)
                .withSockJS(); // WebSocket 미지원 브라우저를 위한 fallback (옵션)
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); 
        // 서버가 메시지를 브로드캐스트할 주소 prefix → 클라이언트는 이걸 구독

        registry.setApplicationDestinationPrefixes("/app");
        // 클라이언트가 메시지를 보낼 때 붙이는 prefix
        // ex) 클라이언트가 "/app/chat/send"로 메시지를 보내면 서버에서는 "/chat/send"로 매핑됨
    }
}
