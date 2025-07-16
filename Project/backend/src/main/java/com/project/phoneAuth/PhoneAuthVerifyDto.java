package com.project.phoneAuth;

//인증 검증 
public class PhoneAuthVerifyDto {
	@NotBlank(message = "휴대폰 번호는 필수입니다.")
    private String phoneNum;

    @NotBlank(message = "인증번호는 필수입니다.")
    private String authNum;
}
