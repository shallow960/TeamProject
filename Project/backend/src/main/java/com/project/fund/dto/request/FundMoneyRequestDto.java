package com.project.fund.dto.request;

import lombok.Getter;
import lombok.Setter;

//후원금 신청
@Getter
@Setter
public class FundMoneyRequestDto {
	
	private String fundSponsor;
	private String memberPhone;
	private String memberBirth;
	private String fundCheck;
	private Integer fundMoney;
	private String fundNote;

}
