package com.project.chat.dto.response;

import java.sql.Timestamp;

import com.project.chat.ChatCheck;
import com.project.chat.entity.ChatEntity;

import lombok.Getter;

@Getter
public class ChatListResponseDto {

    private Integer memberNum;
    private Integer manageNum;
    private String lastChatCont;
    private Timestamp lastSendTime;
    private boolean isNew;

    // 생성자에서 Entity 기반으로 값 세팅 및 isNew 판단
    public ChatListResponseDto(ChatEntity chatEntity) {
        this.memberNum = chatEntity.getMemberNum();
        this.manageNum = chatEntity.getManageNum();
        this.lastChatCont = chatEntity.getChatCont();
        this.lastSendTime = chatEntity.getSendTime();
        this.isNew = chatEntity.getChatCheck() == ChatCheck.N;
    }
}
