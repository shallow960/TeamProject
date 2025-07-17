package com.project.chat.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//채팅방 리스트 조회

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomResponseDto {

	private Integer chatRoomId;
	
	private Integer memberNum;
	
	private Integer adminId;
	
	private Timestamp createdAt;
	
}
