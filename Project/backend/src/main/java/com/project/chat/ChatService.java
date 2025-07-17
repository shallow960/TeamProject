package com.project.chat;


import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.admin.AdminEntity;
import com.project.admin.AdminRepository;
import com.project.chat.dto.ChatRequestDto;
import com.project.chat.exception.ChatException;

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
        entity.setSendTime(new Date(System.currentTimeMillis()));
        entity.setTakeTime(new Date(System.currentTimeMillis()));
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

}
