package com.project.chat.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.chat.entity.ChatCheck;
import com.project.chat.entity.ChatEntity;

public interface ChatRepository extends JpaRepository<ChatEntity, Integer> {

    /**
     * 1) 회원별 최신 채팅 내역 리스트 조회
     * (가장 최근 메시지만 가져오고 싶을 때, DB마다 쿼리가 다를 수 있음)
     * 
     * 예시: MySQL 기준, 최신 메시지 하나를 회원별로 가져오는 쿼리
     */
    @Query(value = "SELECT c1.* FROM chat c1 " +
                   "INNER JOIN (" +
                   "   SELECT member_num, MAX(send_time) AS max_send_time " +
                   "   FROM chat " +
                   "   GROUP BY member_num" +
                   ") c2 ON c1.member_num = c2.member_num AND c1.send_time = c2.max_send_time " +
                   "ORDER BY c1.send_time DESC", nativeQuery = true)
    List<ChatEntity> findLatestChatsGroupedByMember();

    /**
     * 2) 특정 관리번호와 채팅 확인 상태(chatCheck)로 조회
     */
    List<ChatEntity> findByManageNumAndChatCheck(Integer manageNum, ChatCheck chatCheck);

    /**
     * 3) 특정 관리번호의 모든 채팅 내역을 시간순으로 조회
     */
    List<ChatEntity> findByManageNumOrderBySendTimeAsc(Integer manageNum);
    
    /**
     * 4) 특정 회원번호의 모든 채팅 내역을 시간순으로 조회
     */
    List<ChatEntity> findByMemberNumOrderBySendTimeAsc(Integer memberNum);

    /**
     * 5) 채팅 저장 최대 30일 제한
     */
    List<ChatEntity> findByManageNumAndSendTimeAfterOrderBySendTimeAsc(Integer manageNum, Timestamp afterTime);
    
    
    void deleteBySendTimeBefore(Timestamp threshold);

}
