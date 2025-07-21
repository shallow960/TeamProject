package com.project.chat.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatHistoryRequestDto {

    private Integer manageNum; // 특정 회원의 전체 채팅 내역 조회용 관리번호
}
