package com.project.alarm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.alarm.entity.AlarmCheck;
import com.project.alarm.entity.AlarmEntity;

public interface AlarmRepository extends JpaRepository<AlarmEntity, Integer>{
	
	//알림 확인
	List<AlarmEntity> findByMemberNum(Integer memberNum);

	//알림 상태 조회
	List<AlarmEntity> findByMemberNumAndAlarmCheck(Integer memberNum, AlarmCheck alarmCheck);
	
	//알림 삭제
	List<AlarmEntity> deleteByAlarmId(Integer alarmId);
}
