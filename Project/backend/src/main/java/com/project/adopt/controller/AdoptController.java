package com.project.adopt.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.adopt.dto.response.AdoptIndexResponseDto;
import com.project.adopt.dto.request.AdoptScheduleRequestDto;
import com.project.adopt.service.AdoptService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/adopt")
public class AdoptController {
	
	private final AdoptService adoptService;
	
	//관리자가 list에서 '조회' 클릭
	@GetMapping("/{adoptNum}")
	public AdoptIndexResponseDto get(@PathVariable Long adoptNum) {
		return adoptService.get(adoptNum);
	}
	
	//관리자가 list에서 '일정 등록' 클릭 ( /adoptRegist 로 이동)
	@GetMapping("/schedule")
	public String scheduleForm() {
		return "";
	}
	
	//관리자가 list에서 '삭제' 클릭
	@DeleteMapping("/{adoptNum}")
	public void delete(@PathVariable Long adoptNum) {
		adoptService.remove(adoptNum);
	}
	//관리자가 일정등록의 '프로그램 등록' 클릭
	@PostMapping("/adoptRegist")
	public void registerProgram(@RequestBody AdoptScheduleRequestDto dto) {
		adoptService.register(dto);
	}
	
	//관리자가 일정수정의 '프로그램 등록'
	@PutMapping("/adoptRegist/{adoptNum}")
	public void updateProgram(@PathVariable Long adoptNum, @RequestBody AdoptScheduleRequestDto dto) {
		adoptService.modify(adoptNum, dto);
	}
	

}
