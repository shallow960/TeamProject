package com.project.fund.dto.response;

import lombok.Getter;
import lombok.Setter;

//정기후원 신청내역
@Getter
@Setter
public class FundRegularSponResponseDto {
	
	private String memberName;
	private String memberPhone;
	private String memberBirth;
	private String fundCheck;
	private Integer fundMoney;
	private String fundNote;
	private String fundBank;
	private String fundAccountNumber;
	private String fundSponsor;
	private String fundDrawalDate;

}
