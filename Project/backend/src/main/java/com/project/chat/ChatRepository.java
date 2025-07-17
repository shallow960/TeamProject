package com.project.chat;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatEntity, Integer> {
    // 필요시 커스텀 메서드도 여기에 작성
}
