package com.project.fund.dto.response;

import com.project.fund.entity.FundCheck;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FundRegularSponAppResponseDto {

	private String fundSponsor;
	private String fundPhone;
	private String fundBirth;
	private FundCheck fundCheck;
	
	private Integer fundMoney;
	private String fundBank;
	private String fundAccountNum;
	private String fundDepositor;
	
	private String fundDrawalDate;
}
