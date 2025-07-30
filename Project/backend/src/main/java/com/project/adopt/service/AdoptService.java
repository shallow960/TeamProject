package com.project.adopt.service;

import com.project.adopt.dto.request.AdoptScheduleRequestDto;
import com.project.adopt.dto.response.AdoptIndexResponseDto;

public interface AdoptService {
	
	Long register(AdoptScheduleRequestDto adoptScheduleRequestDto); // 입력
	AdoptIndexResponseDto get(Long adoptNum); // 조회
	void modify(Long adoptNum, AdoptScheduleRequestDto dto); //수정
	void remove(Long adoptNum); //삭제

	

}
