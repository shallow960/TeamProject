package com.project.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//채팅 리스트 조회

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseDto {
	
	private Integer memberNum;

	private String manageNum;
	
	private String chatCont;
	
	private String sendTime;
	
	private String chatCheck;
	
}
