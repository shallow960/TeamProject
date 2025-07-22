package com.project.fund.dto.request;

import com.project.fund.entity.FundCheck;
import com.project.member.entity.MemberEntity;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

// 후원 물품 신청서

@Getter
public class FundItemAppRequestDto {
	
	private MemberEntity memberNum;
	
	@NotBlank
	private String fundSponsor;
	@NotBlank
	private String fundPhone;
	@NotBlank
	private String fundBirth;
	@NotBlank
	private FundCheck fundCheck;
	
	
	private String fundItem;
	private String fundNote;
}
