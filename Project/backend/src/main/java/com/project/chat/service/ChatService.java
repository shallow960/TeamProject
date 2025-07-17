package com.project.chat.service;


import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.admin.AdminEntity;
import com.project.admin.AdminRepository;
import com.project.chat.ChatCheck;
import com.project.chat.Entity.ChatEntity;
import com.project.chat.Repository.ChatRepository;
import com.project.chat.dto.ChatRequestDto;
import com.project.chat.exception.ChatException;
import com.project.chat.websocket.ChatMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final AdminRepository adminRepository;

    public void saveChat(ChatRequestDto dto) {
        AdminEntity admin = adminRepository.findFirst()
                .orElseThrow(() -> new RuntimeException("기본 관리자 없음"));

        ChatEntity entity = new ChatEntity();
        entity.setMemberNum(1); // 실제 사용자는 로그인 정보 기반으로 설정해야 함
        entity.setAdminId(admin);
        entity.setChatCont(dto.getChatCont());
        entity.setSendTime(new Timestamp(System.currentTimeMillis()));
        entity.setTakeTime(new Timestamp(System.currentTimeMillis()));
        entity.setChatCheck(ChatCheck.N);

        chatRepository.save(entity);
    }

    public List<ChatEntity> getAllChat() {
        return chatRepository.findAll();
    }

    public ChatEntity getChatById(Integer id) {
        return chatRepository.findById(id)
                .orElseThrow(() -> new ChatException("해당 ID의 채팅이 존재하지 않습니다. ID: " + id));
    }
    
    //웹 소켓에서 들어오는 메시지 저장
    public ChatEntity saveChatViaSocket(ChatMessage message) {
        AdminEntity admin = adminRepository.findFirst() // 관리자 entity를 ID로 조회
                .orElseThrow(() -> new RuntimeException("기본 관리자 없음"));

        ChatEntity entity = new ChatEntity();
        entity.setMemberNum(1); // 임시, 실제로는 클라이언트/세션 기반 설정 필요
        entity.setAdminId(admin);
        entity.setChatCont(message.getContent());
        entity.setSendTime(new Timestamp(System.currentTimeMillis()));
        entity.setTakeTime(new Timestamp(System.currentTimeMillis()));
        entity.setChatCheck(ChatCheck.N);

        return chatRepository.save(entity);
    }


}
