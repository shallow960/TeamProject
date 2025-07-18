package com.project.chat.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatAdminRequestDto {

    private String manageNum; // 어떤 회원에게 보낼지 지정
    private String chatCont;  // 전송할 내용
}