package com.project.chat.dto.request;

import com.project.chat.entity.ChatEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatSocketRequestDto {

	private Integer manageNum;
    private String chatCont;
    private String senderType; // "USER" 또는 "ADMIN" 등
    
    public ChatSocketRequestDto(ChatEntity chatEntity) {
    	this.manageNum = chatEntity.getManageNum();
        this.chatCont = chatEntity.getChatCont();

        // adminId가 null인지 여부로 사용자/관리자 판단
        this.senderType = (chatEntity.getAdminId() == null) ? "USER" : "ADMIN";
    }
}
