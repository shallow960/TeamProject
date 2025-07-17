package com.project.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//채팅 방 생성 요청

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateChatRoomRequestDto {
    private Integer memberNum;
}
