package com.project.fund.dto.request;

import lombok.Getter;
import lombok.Setter;

//후원 물품 신청
@Getter
@Setter
public class FundItemRequestDto {
	
	private String fundSponsor;
	private String memberPhone;
	private String memberBirth;
	private String fundCheck;
	private String fundItem;
	private String fundNote;
	

}
