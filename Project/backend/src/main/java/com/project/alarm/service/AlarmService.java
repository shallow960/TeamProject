package com.project.alarm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.alarm.entity.AlarmCheck;
import com.project.alarm.entity.AlarmEntity;
import com.project.alarm.repository.AlarmRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {

	private final AlarmRepository alarmRepository;
	
	//알림 생성
	@Transactional(readOnly = true)
	public List<AlarmEntity> getAlarmContentByMember(Integer memberNum){
		return alarmRepository.findByMemberNum(memberNum);
	}
	
	//알림 확인
	@Transactional
	public void markAlarmRead(Integer memberNum) {
		List<AlarmEntity> unreadAlarm = alarmRepository.findByMemberNumAndAlarmCheck(memberNum, AlarmCheck.N);
		return AlarmEntity.setAlarmCheck(AlarmCheck.Y);
	}
	//알림 삭제
	public void deleteAlarmContent() {
		alarmRepository.deleteByAlarmContent();
	}
	
}
