package com.project.fund.entity;

import java.sql.Date;

import com.project.member.entity.MemberEntity;

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
	@JoinColumn(name = "member_num",nullable = true)
	private MemberEntity memberNum; // 회원번호
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "member_id", nullable = true)
//    private String memberId; //이메일 아이디

	@Column(name = "fund_sponsor",nullable=false)
	private String fundSponsor; //후원자명
	
	@Column(name = "fund_phone",nullable=false)
	private String fundPhone; // 연락처
	
	@Column(name = "fund_birth",nullable=false)
	private String fundBirth; // 생년월일
	
	@Column(name = "fund_money")
	private Integer fundMoney;	// 후원 금액
	
	@Column(name = "fund_time", nullable=false)
	private Date fundTime;	// 후원 일시
	
	@Column(name = "sum_money")
	private String sumMoney; // 후원 총 금액
	
	@Column(name = "fund_item") 
	private String fundItem; // 후원 물품
	
	@Column(name = "fund_note")
	private String fundNote; // 비고
	
	@Column(name = "fund_bank")
	private String fundBank; // 은행명
	
	@Column(name = "fund_accountnum")
	private String fundAccountNum; // 계좌번호
	
	@Column(name = "fund_depositor")
	private String fundDepositor; // 예금주명

	@Column(name = "fund_drawaldate")
	private String fundDrawalDate; // 출금일

	
	@Enumerated(EnumType.STRING)
	@Column(name = "fund_check",nullable=false)
	private FundCheck fundCheck;  //확인 상태 "Y","N"
	
	
}