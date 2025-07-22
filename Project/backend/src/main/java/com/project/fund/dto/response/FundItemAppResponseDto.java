package com.project.fund.dto.response;

import com.project.fund.entity.FundCheck;

import lombok.Getter;
import lombok.Setter;

// 후원물품 신청 내역

@Setter
@Getter
public class FundItemAppResponseDto {

	private String fundSponsor;
	private String fundPhone;
	private String fundBirth;
	private FundCheck fundCheck;
	private String fundItem;
	private String fundNote;
	
}
