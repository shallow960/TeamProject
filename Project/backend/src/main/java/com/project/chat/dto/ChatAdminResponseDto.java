package com.project.chat.dto;

import java.sql.Timestamp;

import com.project.chat.entity.ChatEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatAdminResponseDto {

    private String chatCont;
    private Timestamp sendTime;
    private String chatCheck;
    private String senderType; // "USER" or "ADMIN"

    public ChatAdminResponseDto(ChatEntity chatEntity) {
        this.chatCont = chatEntity.getChatCont();
        this.sendTime = chatEntity.getSendTime();
        this.chatCheck = chatEntity.getChatCheck().name();
        this.senderType = (chatEntity.getAdminId() == null) ? "USER" : "ADMIN";
    }
}
