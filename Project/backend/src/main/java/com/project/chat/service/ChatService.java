package com.project.chat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.chat.ChatCheck;
import com.project.chat.dto.ChatAdminResponseDto;
import com.project.chat.dto.ChatHistoryRequestDto;
import com.project.chat.dto.ChatListResponseDto;
import com.project.chat.dto.ChatMarkReadRequestDto;
import com.project.chat.entity.ChatEntity;
import com.project.chat.repository.ChatRepository;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    // 생성자 주입 방식 (스프링 권장)
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    /**
     * 1) 관리자용 - 회원별 최근 메시지 목록 조회
     */
    @Transactional(readOnly = true) // 읽기 전용 트랜잭션 설정
    public List<ChatListResponseDto> getRecentChatsByAdmin() {
        // DB에서 회원별 최근 메시지 목록 조회하는 메서드 호출 (쿼리는 Repository에 구현 필요)
        List<ChatEntity> chatEntities = chatRepository.findLatestChatsGroupedByMember();

        // DTO 리스트 생성
        List<ChatListResponseDto> dtos = new ArrayList<>();

        // Entity -> DTO 변환 반복문
        for (ChatEntity entity : chatEntities) {
            // 각 엔티티를 기반으로 DTO 생성자 호출
            ChatListResponseDto dto = new ChatListResponseDto(entity);

            // 변환된 DTO 리스트에 추가
            dtos.add(dto);
        }

        // 완성된 DTO 리스트 반환
        return dtos;
    }

    /**
     * 2) 관리자용 - 회원 채팅 읽음 상태(Y)로 변경
     */
    @Transactional
    public void markChatsAsRead(ChatMarkReadRequestDto requestDto) {
        // 요청 DTO에서 관리번호 읽어오기
        String manageNum = requestDto.getManageNum();

        // DB에서 해당 관리번호에 대해 읽지 않은 채팅(N) 모두 조회
        List<ChatEntity> unreadChats = chatRepository.findByManageNumAndChatCheck(manageNum, ChatCheck.N);

        // 읽지 않은 채팅 하나씩 읽음(Y) 상태로 변경
        for (ChatEntity chat : unreadChats) {
            chat.setChatCheck(ChatCheck.Y);

            // 변경된 엔티티 저장 (DB 업데이트)
            chatRepository.save(chat);
        }
    }

    /**
     * 3) 관리자용 - 특정 회원의 전체 채팅 내역 조회
     */
    @Transactional(readOnly = true)
    public List<ChatAdminResponseDto> getChatHistory(ChatHistoryRequestDto requestDto) {
        // 요청 DTO에서 관리번호 읽어오기
        String manageNum = requestDto.getManageNum();

        // DB에서 해당 관리번호의 모든 채팅 내역 시간순 조회
        List<ChatEntity> chatEntities = chatRepository.findByManageNumOrderBySendTimeAsc(manageNum);

        // 반환용 DTO 리스트 생성
        List<ChatAdminResponseDto> dtos = new ArrayList<>();

        // Entity -> DTO 변환 반복문
        for (ChatEntity entity : chatEntities) {
            // 각 엔티티를 기반으로 DTO 생성자 호출
            ChatAdminResponseDto dto = new ChatAdminResponseDto(entity);

            // 리스트에 추가
            dtos.add(dto);
        }

        // 완성된 리스트 반환
        return dtos;
    }
}
