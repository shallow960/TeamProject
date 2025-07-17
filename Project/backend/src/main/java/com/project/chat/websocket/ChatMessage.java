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
	
	private Integer adminId;	// 관리자일 경우
	private Integer memberNum;	// 회원일 경우
    private String content;    // 실제 메시지
    private String timestamp;  // 전송 시간

}

