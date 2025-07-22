package com.project.fund.dto.request;

import com.project.fund.entity.FundCheck;
import com.project.member.entity.MemberEntity;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FundRegularSponAppRequestDto {

	private MemberEntity memberNum;
	
	@NotBlank
	private String fundSponsor;
	@NotBlank
	private String fundPhone;
	@NotBlank
	private String fundBirth;
	@NotBlank
	private FundCheck fundCheck;
	
	@NotBlank
	private Integer fundMoney;
	@NotBlank
	private String fundBank;
	@NotBlank
	private String fundAccountNum;
	@NotBlank
	private String fundDepositor;
	@NotBlank
	private String fundDrawalDate;
	
}
