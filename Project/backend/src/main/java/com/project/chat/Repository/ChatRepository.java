package com.project.chat.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.chat.Entity.ChatEntity;

public interface ChatRepository extends JpaRepository<ChatEntity, Integer> {
    // 필요시 커스텀 메서드도 여기에 작성
}
