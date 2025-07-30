package com.project.animal.service;

import com.project.animal.dto.request.AnimalRequestDto;
import com.project.animal.dto.response.AnimalResponseDto;

public interface AnimalService {
	
	Long register(AnimalRequestDto animalRequestDto); // 입력
	AnimalResponseDto get(Long AnimalId); //조회
	void modify(Long animalNum, AnimalRequestDto dto); //수정
	void remove(Long animalNum); // 삭제
	
	
	
}
