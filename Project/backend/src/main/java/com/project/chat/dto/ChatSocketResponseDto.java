package com.project.chat.dto;

import com.project.chat.entity.ChatEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatSocketResponseDto {

    private Integer manageNum;
    private String chatCont;
    private String sendTime;
    private String senderType; // "USER" 또는 "ADMIN"
    private String chatCheck;  // "Y" 또는 "N"

    public ChatSocketResponseDto(ChatEntity chatEntity) {
        this.manageNum = chatEntity.getManageNum();
        this.chatCont = chatEntity.getChatCont();
        this.sendTime = chatEntity.getSendTime().toString(); // 또는 DateTimeFormatter 적용
        this.senderType = (chatEntity.getAdminId() == null) ? "USER" : "ADMIN";
        this.chatCheck = chatEntity.getChatCheck().name(); // enum -> 문자열
    }
}
