package com.project.chat.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
	
	private Integer adminId;
	private Integer memberNum;
    private String content;    // 실제 메시지
    private String timestamp;  // 전송 시간

}

