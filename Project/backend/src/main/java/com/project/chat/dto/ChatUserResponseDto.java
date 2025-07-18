package com.project.chat.dto;

import java.sql.Timestamp;

import com.project.chat.entity.ChatEntity;

import lombok.Getter;

@Getter
public class ChatUserResponseDto {
    private String chatCont;
    private Timestamp sendTime;
    private String chatCheck;
    private String senderType; // 여기만 추가적으로 판단

    public ChatUserResponseDto(ChatEntity chatEntity) {
        this.chatCont = chatEntity.getChatCont();
        this.sendTime = chatEntity.getSendTime();
        this.chatCheck = chatEntity.getChatCheck().name();

        // adminId가 null인지 여부로 사용자/관리자 판단
        this.senderType = (chatEntity.getAdminId() == null) ? "USER" : "ADMIN";
    }
}
