package com.project.alarm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.alarm.entity.AlarmCheck;
import com.project.alarm.entity.AlarmEntity;

public interface AlarmRepository extends JpaRepository<AlarmEntity, Long> {

    // 회원번호로 알림 전체 조회
    List<AlarmEntity> findByMemberNum_MemberNum(Long memberNum);

    // 회원번호 + 안읽은 알림만 조회
    List<AlarmEntity> findByMemberNum_MemberNumAndAlarmCheck(Long memberNum, AlarmCheck alarmCheck);
    
    // 알림 삭제
    List<AlarmEntity> deleteByAlarmId(Long alarmId);
}
