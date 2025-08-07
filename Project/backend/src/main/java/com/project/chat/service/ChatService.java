package com.project.chat.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.chat.dto.request.ChatAdminRequestDto;
import com.project.chat.dto.request.ChatHistoryRequestDto;
import com.project.chat.dto.request.ChatMarkReadRequestDto;
import com.project.chat.dto.request.ChatSocketRequestDto;
import com.project.chat.dto.request.ChatUserRequestDto;
import com.project.chat.dto.response.ChatAdminResponseDto;
import com.project.chat.dto.response.ChatListResponseDto;
import com.project.chat.dto.response.ChatUserResponseDto;
import com.project.chat.entity.ChatCheck;
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
        Integer manageNum = requestDto.getManageNum();

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
        Integer manageNum = requestDto.getManageNum();

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
    /**
     * 고객이 채팅 메시지를 보낼 때 저장하는 메서드
     */
    @Transactional
    public ChatEntity saveUserChat(Integer memberNum, Integer manageNum, ChatUserRequestDto requestDto) {
        ChatEntity chat = new ChatEntity();

        // 회원번호 세팅 (인증 정보 등에서 가져올 수 있음)
        chat.setMemberNum(memberNum);

        // 관리번호 세팅 (필요 시 매핑)
        chat.setManageNum(manageNum);

        // 채팅 내용 세팅 (사용자가 보낸 메시지)
        chat.setChatCont(requestDto.getChatCont());

        // 보낸 시간 현재 시각으로 세팅
        chat.setSendTime(new Timestamp(System.currentTimeMillis()));

        // 받은 시간은 null 또는 동일하게 설정 가능 (운영 정책에 따라)
        chat.setTakeTime(null);

        // 읽음 여부 초기값 N (관리자가 확인 전)
        chat.setChatCheck(ChatCheck.N);

        // 관리자 아이디는 null (사용자 메시지이므로)
        chat.setAdminId(null);

        // 저장 후 반환
        return chatRepository.save(chat);
    }

    /**
     * 고객이 자신의 채팅 내역을 조회하는 메서드
     */
    @Transactional(readOnly = true)
    public List<ChatUserResponseDto> getUserChatHistory(Integer memberNum) {
        // memberNum 기준으로 모든 채팅 내역 조회 (시간순)
        List<ChatEntity> chatEntities = chatRepository.findByMemberNumOrderBySendTimeAsc(memberNum);

        List<ChatUserResponseDto> dtos = new ArrayList<>();
        for (ChatEntity entity : chatEntities) {
            ChatUserResponseDto dto = new ChatUserResponseDto(entity);
            dtos.add(dto);
        }

        return dtos;
    }
    /**
     *  관리자가 사용자에게 메시지를 보내는 메서드
     */
    @Transactional
    public void saveAdminChat(ChatAdminRequestDto requestDto) {
        ChatEntity chat = new ChatEntity();

        // 관리번호 세팅 (누구와 대화 중인지 식별)
        chat.setManageNum(requestDto.getManageNum());

        // 회원번호는 null로 둘 수 있음 (관리자 기준이므로)
        chat.setMemberNum(null);

        // 채팅 내용 세팅
        chat.setChatCont(requestDto.getChatCont());

        // 현재 시간으로 보낸 시간 세팅
        chat.setSendTime(new Timestamp(System.currentTimeMillis()));

        // 받은 시간은 null 또는 운영 정책에 따라
        chat.setTakeTime(null);

        // 읽음 여부는 Y (관리자가 보낸 메시지이므로 이미 본 것으로 처리)
        chat.setChatCheck(ChatCheck.Y);

        // 관리자 ID 세팅
        //chat.setAdminId(requestDto.getAdminId());

        // DB 저장
        chatRepository.save(chat);
    }
    
    // 관리자가 확인시 N -> Y 전환
    @Transactional
    public void markChatAsRead(Integer manageNum) {
        List<ChatEntity> unreadChats = chatRepository.findByManageNumAndChatCheck(manageNum, ChatCheck.N);
        for (ChatEntity chat : unreadChats) {
            chat.setChatCheck(ChatCheck.Y);
        }
        // dirty checking으로 save 생략 가능 (JPA flush 시점)
    }
    
    
    /* 소켓 상호 채팅 저장
     * */
    @Transactional
    public ChatEntity saveChatSocket(ChatSocketRequestDto dto) {
        ChatEntity chat = new ChatEntity();

        chat.setManageNum(dto.getManageNum());
        chat.setChatCont(dto.getChatCont());
        chat.setSendTime(new Timestamp(System.currentTimeMillis()));

        if ("ADMIN".equalsIgnoreCase(dto.getSenderType())) {
            chat.setMemberNum(null);
            chat.setChatCheck(ChatCheck.Y);
            chat.setAdminId(null);
            chat.setTakeTime(null);
        } else {
            chat.setMemberNum(dto.getManageNum()); // 또는 필요시 다른 값
            chat.setChatCheck(ChatCheck.N);
            chat.setAdminId(null); // 추후 값 기입 필요
            chat.setTakeTime(null);
        }

        return chatRepository.save(chat);
    }

    // 매일 새벽3시에 db 내의 30일지난 보관내용 삭제
    @Scheduled(cron = "0 0 3 * * *") // 매일 새벽 3시
    public void deleteOldChats() {
        Timestamp threshold = Timestamp.valueOf(LocalDateTime.now().minusDays(30));
        chatRepository.deleteBySendTimeBefore(threshold);
    }


    
}
