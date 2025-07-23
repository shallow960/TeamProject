package com.project.fund.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.fund.dto.request.FundItemAppRequestDto;
import com.project.fund.dto.request.FundRegularSponAppRequestDto;
import com.project.fund.dto.request.FundSponAppRequestDto;
import com.project.fund.dto.response.FundItemAppResponseDto;
import com.project.fund.dto.response.FundRegularSponAppResponseDto;
import com.project.fund.dto.response.FundSponAppResponseDto;
import com.project.fund.entity.FundEntity;
import com.project.fund.service.FundService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/") //미정
public class FundController {
	
	private final FundService fundService;
	
	//사용자가 (후원금 신청) 후원하기 버튼을 눌렀을 때
	@PostMapping("/")
	public ResponseEntity<?> saveFundSponApp(@RequestBody FundSponAppRequestDto dto){
		fundService.saveSponApp(dto);
		return ResponseEntity.ok("후원 완료");
	}
	
	//사용자가 (후원물품 신청) 후원하기 버튼 눌렀을 때
	@PostMapping("/")
	public ResponseEntity<?> saveFundItemApp(@RequestBody FundItemAppRequestDto dto){
		fundService.saveItemApp(dto);
		return ResponseEntity.ok("후원 완료");
	}
	
	//사용자가 (정기후원 신청) 후원하기 버튼 눌렀을 때
	@PostMapping("/")
	public ResponseEntity<?> saveRegularSponApp(@RequestBody FundRegularSponAppRequestDto dto){
		fundService.saveRegularSponApp(dto);
		return ResponseEntity.ok("후원 완료");
	}
	
	//회원 (후원금 신청) 후원하기 버튼 클릭 후 내역 조회
	@PostMapping("/")
	public ResponseEntity<?> getFundSponApp(@RequestBody FundSponAppResponseDto dto){
		List<FundEntity> result = fundService.getFundListByGuest(dto.getFundSponsor(), dto.getFundPhone());
		return ResponseEntity.ok(result);
	}
	
	//사용자가 (후원물품 신청) 후원하기 버튼 클릭 후 내역 조회
	@PostMapping("/")
	public ResponseEntity<?> getFundItemApp(@RequestBody FundItemAppResponseDto dto){
		List<FundEntity> result = fundService.getFundListByGuest(dto.getFundSponsor(),dto.getFundPhone());
		return ResponseEntity.ok(result);
				
				
	}
	
	//사용자가 (정기 후원) 후원하기 버튼 클릭 후 내역 조회
	@PostMapping("/")
	public ResponseEntity<?> getFundRegularSponApp(@RequestBody FundRegularSponAppResponseDto dto){
		List<FundEntity> result = fundService.getFundListByGuest(dto.getFundSponsor(),dto.getFundPhone());
		return ResponseEntity.ok(result);
	}

}
