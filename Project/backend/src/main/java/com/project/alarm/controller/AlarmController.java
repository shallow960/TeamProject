package com.project.alarm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.alarm.service.AlarmService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")//미정
public class AlarmController {

	private final AlarmService alarmService;
	
	//알림 전체조회
	//알림 단일조회
	//알림 삭제
	//관리자가 사용자에게 알림 보내기(놀이터(land),봉사(volunteer)의 신청,변경 시 자동 알림 생성)
	
}
