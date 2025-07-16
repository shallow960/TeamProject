package com.project.chat;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
	
	private final ChatService chatService;
	
	// 채팅 등록
	@PostMapping
	public ResponseEntity<String> createChat(@RequestBody ChatDto chatDto){
		ChatEntity chatEntity = new ChatEntity();
		chatEntity.setChatCont(chatDto.getChatCont());
		
		chatService.saveChat(chatEntity);
		return ResponseEntity.ok("채팅이 저장되었습니다.");
	}
	
	// 채팅 전체 조회
	@GetMapping
	public ResponseEntity<List<ChatDto>> getAllChats(){
		List<ChatDto> chatList = chatService.getAllChat()
				.stream()
				.map(chat -> new ChatDto(
						chat.getAdmin().getAdminId().intValue(),
						chat.getChatCont(),
						chat.getSendTime().toString(),
						chat.getChatCheck().name()
				))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(chatList);
	}
	
	// 채팅 단일 조회 
	@GetMapping("/{id}")
	public ResponseEntity<ChatDto> getChatById(@PathVariable Integer id){
		ChatEntity chat = chatService.getChatById(id);
		ChatDto dto = new ChatDto(
				chat.getAdmin().getAdminId().intValue(),
				chat.getChatCont(),
				chat.getSendTime().toString(),
				chat.getChatCheck().name()
		);
		
		return ResponseEntity.ok(dto);
	}
	
	
}
