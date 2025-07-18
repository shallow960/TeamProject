package com.project.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMarkReadRequestDto {

    private Integer manageNum; // 해당 회원의 관리번호 (채팅 읽음 처리 대상)
}
