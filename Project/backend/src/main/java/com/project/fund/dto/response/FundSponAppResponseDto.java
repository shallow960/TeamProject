package com.project.fund.dto.response;

import com.project.fund.entity.FundCheck;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FundSponAppResponseDto {
	
	private String fundSponsor;
	private String fundPhone;
	private String fundBirth;
	private FundCheck fundCheck;
	private Integer fundMoney;
	private String fundNote;
}
