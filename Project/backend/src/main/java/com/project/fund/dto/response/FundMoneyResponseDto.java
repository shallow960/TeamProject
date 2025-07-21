package com.project.fund.dto.response;

import lombok.Getter;
import lombok.Setter;


//후원금 신청내역
@Getter
@Setter
public class FundMoneyResponseDto {
	
	private String fundSponsor;
	private String memberPhone;
	private String memberBirth;
	private String fundCheck;
	private Integer fundMoney;
	private String fundNote;

}
