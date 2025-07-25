package com.project.fund.dto.request;

import com.project.fund.entity.FundCheck;
import com.project.member.entity.MemberEntity;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

// 후원금 신청서

@Getter
@Builder
public class FundSponAppRequestDto {
	
	private MemberEntity memberNum;
	
	@NotBlank
	private String fundSponsor;
	@NotBlank
	private String fundPhone;
	@NotBlank
	private String fundBirth;
	@NotBlank
	private FundCheck fundCheck;
	
	private Integer fundMoney;
	private String fundNote;
	
}
