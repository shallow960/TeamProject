package com.project.chat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.chat.dto.ChatAdminRequestDto;
import com.project.chat.dto.ChatAdminResponseDto;
import com.project.chat.dto.ChatHistoryRequestDto;
import com.project.chat.dto.ChatListResponseDto;
import com.project.chat.dto.ChatUserRequestDto;
import com.project.chat.service.ChatService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor // final로 선언된 chatService를 생성자 주입
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    // ✅ 1. 사용자가 관리자에게 메시지를 전송할 때 사용하는 API
    @PostMapping("/user/{memberNum}/send")
    public ResponseEntity<?> sendUserChat(
            @PathVariable Integer memberNum,                        // URL 경로에서 사용자 번호 추출
            @Valid @RequestBody ChatUserRequestDto requestDto       // 사용자 채팅 요청 본문 수신 (JSON → DTO 매핑)
    ) {
        // 임의의 관리 번호 (예: 관리자 ID는 실제 시스템에 따라 구하거나 지정해야 함)
        Integer manageNum = 1; // 실제로는 사용자 번호에 따라 자동 지정해야 함

        // 사용자 채팅 저장을 서비스에 요청
        chatService.saveUserChat(memberNum, manageNum, requestDto);

        // 성공 응답 반환
        return ResponseEntity.ok("✅ 사용자 채팅 저장 완료");
    }

    // ✅ 2. 관리자가 최근 사용자별 마지막 채팅만 목록으로 보는 API
    @GetMapping("/admin/recent")
    public ResponseEntity<List<ChatListResponseDto>> getRecentChatsByAdmin() {

        // ChatService에서 최근 채팅 목록 가져오기
        List<ChatListResponseDto> chatList = chatService.getRecentChatsByAdmin();

        // 해당 목록을 클라이언트에게 반환
        return ResponseEntity.ok(chatList);
    }

    // ✅ 3. 관리자가 특정 회원의 전체 채팅 내역을 조회하는 API
    @GetMapping("/admin/history/{manageNum}")
    public ResponseEntity<List<ChatAdminResponseDto>> getChatHistory(@PathVariable Integer manageNum) {
        
        // 1. DTO 객체 생성
        ChatHistoryRequestDto requestDto = new ChatHistoryRequestDto();
        
        // 2. URL 경로로 받은 manageNum을 DTO에 세팅
        requestDto.setManageNum(manageNum);

        // 3. 서비스 메서드에 DTO 객체를 넘겨 호출
        List<ChatAdminResponseDto> chatList = chatService.getChatHistory(requestDto);

        // 4. 결과를 ResponseEntity로 감싸 반환
        return ResponseEntity.ok(chatList);
    }



    // ✅ 4. 관리자가 특정 회원의 채팅을 클릭하여 확인 상태로 변경하는 API
    @PutMapping("/admin/mark-read/{manageNum}")
    public ResponseEntity<?> markChatAsRead(
            @PathVariable Integer manageNum
    ) {
        // 서비스에서 확인 상태(chatCheck)를 'Y'로 업데이트 처리
        chatService.markChatAsRead(manageNum);

        // 성공 메시지 반환
        return ResponseEntity.ok("✅ 확인 상태로 변경 완료");
    }

    // ✅ 5. 관리자가 회원에게 채팅을 전송하는 API
    @PostMapping("/admin/send")
    public ResponseEntity<?> sendAdminChat(
            @Valid @RequestBody ChatAdminRequestDto requestDto // 관리자 채팅 요청 데이터 수신
    ) {
        // 서비스에 저장 요청
        chatService.saveAdminChat(requestDto);

        // 응답 반환
        return ResponseEntity.ok("✅ 관리자 채팅 저장 완료");
    }
}
