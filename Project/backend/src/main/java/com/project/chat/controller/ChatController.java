package com.project.chat.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.chat.ChatEntity;
import com.project.chat.ChatService;
import com.project.chat.dto.ChatRequestDto;
import com.project.chat.dto.ChatResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    
     //	채팅 등록 (사용자 메시지 저장)
   
    @PostMapping
    public ResponseEntity<String> createChat(@RequestBody ChatRequestDto chatDto) {
        chatService.saveChat(chatDto); // Service에서 Entity 생성 & 저장
        return ResponseEntity.ok("채팅이 저장되었습니다.");
    }

    //	전체 채팅 조회
    @GetMapping
    public ResponseEntity<List<ChatResponseDto>> getAllChats() {
        List<ChatResponseDto> chatList = chatService.getAllChat()
                .stream()
                .map(chat -> new ChatResponseDto(
                        chat.getMemberNum(),
                        chat.getManageNum(), // 타입 맞게 수정됨
                        chat.getChatCont(),
                        chat.getSendTime().toString(),
                        chat.getChatCheck().name()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(chatList);
    }
    
    // 단일 채팅 조회
    @GetMapping("/{id}")
    public ResponseEntity<ChatResponseDto> getChatById(@PathVariable Integer id) {
        ChatEntity chat = chatService.getChatById(id);

        ChatResponseDto dto = new ChatResponseDto(
                chat.getMemberNum(),
                chat.getManageNum(),
                chat.getChatCont(),
                chat.getSendTime().toString(),
                chat.getChatCheck().name()
        );

        return ResponseEntity.ok(dto);
    }

}
