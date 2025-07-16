package com.project.phoneAuth;

//인증요청 사용자 > 관리자
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class PhoneAuthRequestDto {
	private String PhoneNum; //휴대폰번호
}
