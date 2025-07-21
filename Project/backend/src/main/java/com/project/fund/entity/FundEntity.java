package com.project.fund.entity;

import java.sql.Date;

import com.project.member.MemberEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "fund")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FundEntity {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_num", nullable = false)
	private MemberEntity member;

	
	@Column(name = "fund_money")
	private String fundMoney;	// 후원 금액
	
	@Column(name = "fund_time", nullable=false)
	private Date fundTime;	// 후원 일시
	
	@Column(name = "sum_money")
	private String sumMoney; //후원 총 금액
	
	@Column(name = "fund_item") // 후원 물품
	private String fundItem;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "fund_check", length = 1, nullable=false)
	private FundCheck fundCheck;  //확인 상태 "Y","N"
	
	@Column(name = "fund_note")
	private String fundNote; //비고
	
	@Column(name = "fund_bank")
	private String fundBank; //은행명
	
	@Column(name = "fund_accountnumber")
	private String fundAccountNumber; //계좌번호
	
	@Column(name = "fund_sponsor")
	private String fundSponsor; //후원자명
	
	@Column(name = "fund_drawaldate")
	private String fundDrawalDate; //출금일
}

//	CREATE TABLE chat (
//	    member_num INT AUTO_INCREMENT PRIMARY KEY,
//		fund_money VARCHAR(255),
//		fund_time DATETIME,
//		sum_money VARCHAR(255)
//	);