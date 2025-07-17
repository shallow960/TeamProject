package com.project.chat.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.chat.Entity.ChatRoomEntity;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Integer> {

    // 필요 시 추가: 회원별 채팅방 목록
    // List<ChatRoomEntity> findByMember_Id(Integer memberNum);


}