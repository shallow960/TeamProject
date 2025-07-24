package com.project.alarm.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.alarm.dto.request.AlarmRequestDto;
import com.project.alarm.dto.response.AlarmResponseDto;
import com.project.alarm.entity.AlarmCheck;
import com.project.alarm.entity.AlarmEntity;
import com.project.alarm.repository.AlarmRepository;
import com.project.member.entity.MemberEntity;
import com.project.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {

	private final AlarmRepository alarmRepository;
	private final MemberRepository memberRepository;
	
	//회원의 알림 조회
	@Transactional(readOnly = true)
	public List<AlarmResponseDto> getAlarmContentByMember(Long memberNum) {
	    List<AlarmEntity> alarms = alarmRepository.findByMemberNum_MemberNum(memberNum);
	    return alarms.stream()
	            .map(AlarmResponseDto::fromEntity)
	            .toList();
	}

	
	//해당 알림 확인
	@Transactional
	public void markAlarmRead(Long alarmId) {
		AlarmEntity alarm = alarmRepository.findById(alarmId)
				.orElseThrow(() -> new RuntimeException("알림이 없습니다."));
		alarm.setAlarmCheck(AlarmCheck.Y);
	}
	
	//알림 삭제
	@Transactional
	public void deleteAlarm(Long alarmId) {
		alarmRepository.deleteById(alarmId);
	}
	
	//알림 생성
	@Transactional
	public void createAlarm(AlarmRequestDto dto) {
		MemberEntity member = memberRepository.findById(dto.getMemberNum())
				.orElseThrow(() -> new RuntimeException("존재하지 않은 회원"));
		
		AlarmEntity alarm = AlarmEntity.builder()
				.memberNum(member)
				.alarmTitle(dto.getAlarmTitle())
				.alarmContent(dto.getAlarmContent())
				.alarmURL(dto.getAlarmURL())
				.alarmCheck(AlarmCheck.N)
				.alarmTime(new Timestamp(System.currentTimeMillis()))
				.build();
		
		alarmRepository.save(alarm);
	}
}
