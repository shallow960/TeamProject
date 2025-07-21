package com.project.fund.dto.response;

import lombok.Getter;
import lombok.Setter;

//물품 후원 신청내역
@Getter
@Setter
public class FundItemResponseDto {

	private String fundSponsor;
	private String memberPhone;
	private String memberBirth;
	private String fundCheck;
	private String fundItem;
	private String fundNote;
	
}
