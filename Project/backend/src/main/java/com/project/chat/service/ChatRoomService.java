package com.project.chat.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.admin.AdminEntity;
import com.project.chat.Entity.ChatRoomEntity;
import com.project.chat.Repository.ChatRoomRepository;
import com.project.chat.dto.ChatRoomResponseDto;
import com.project.chat.dto.CreateChatRoomRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
	
	private final ChatRoomRepository chatRoomRepository;
	private final AdminRepository adminRepository;
	
	public ChatRoomResponseDto createChatRoom(CreateChatRoomRequestDto dto) {
        Integer memberNum = dto.getMemberNum();

        // 관리자는 임시로 첫 번째 관리자 지정
        AdminEntity admin = adminRepository.findFirst()
            .orElseThrow(() -> new RuntimeException("기본 관리자 없음"));

        // 이미 채팅방이 존재하면 재사용
        Optional<ChatRoomEntity> existingRoom = chatRoomRepository.findByMemberNumAndAdmin_Id(memberNum, admin.getAdmin_id());
        if (existingRoom.isPresent()) {
            ChatRoomEntity room = existingRoom.get();
            return new ChatRoomResponseDto(room.getChatRoomId(), memberNum, admin.getAdmin_id(), room.getCreatedAt());
        }

        // 없으면 새로 생성
        ChatRoomEntity room = new ChatRoomEntity();
        room.setAdminId(admin);
        room.setMemberNum(memberNum);
        room.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        ChatRoomEntity saved = chatRoomRepository.save(room);

        return new ChatRoomResponseDto(saved.getChatRoomId(), memberNum, admin.getChatRoomId(), saved.getCreatedAt());
    }

    public List<ChatRoomResponseDto> getAllRooms() {
        return chatRoomRepository.findAll().stream()
            .map(room -> new ChatRoomResponseDto(
                room.getChatRoomId(),
                room.getMemberNum(),
                room.getAdminId().getChatRoomId(),
                room.getCreatedAt()
            ))
            .collect(Collectors.toList());
    }
}

