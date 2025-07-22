package com.project.fund.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.fund.dto.request.FundSponAppRequestDto;
import com.project.fund.service.FundService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/") //미정
public class FundController {
	
	private final FundService fundEntity;
	
	@PostMapping("/")
	public ResponseEntity<?> saveFundSponApp(
			@RequestBody @Valid FundSponAppRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails){
		Long memberId = userDetails.getMemberId();
		FundService.saveSponApp(dto, memberId);
		
		return ResponseEntity.ok("후원신청 완료")
	}
	

}
