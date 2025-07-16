package com.project.chat;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatRepository chatRepository;
	
	public void saveChat(ChatEntity entity) {
		chatRepository.save(entity);
	}
	
	public List<ChatEntity> getAllChat(){
		return chatRepository.findAll();
	}
	
	
	
	
	
	
	// 예외처리
	public ChatEntity getChatById(Integer id) {
		return chatRepository.findById(id)
				.orElseThrow(() -> new ChatException("채팅 ID " + id + "를 찾을 수 없습니다."));
		
	}
}
