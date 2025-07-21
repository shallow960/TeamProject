package com.project.fund.dto.request;

import lombok.Getter;
import lombok.Setter;

//정기 후원 신청
@Getter
@Setter
public class FundRegularSponRequestDto {

	private String memberName;
	private String memberPhone;
	private String memberBirth;
	private String fundCheck;
	private Integer fundMoney;
	private String fundBank;
	private String fundAccountNumber;
	private String fundSponsor;
	private String fundDrawalDate;
	
}
