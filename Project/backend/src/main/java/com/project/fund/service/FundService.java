package com.project.fund.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.fund.dto.request.FundItemAppRequestDto;
import com.project.fund.dto.request.FundRegularSponAppRequestDto;
import com.project.fund.dto.request.FundSponAppRequestDto;
import com.project.fund.entity.FundEntity;
import com.project.fund.repository.FundRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FundService {

	private final FundRepository fundRepository;
	
	//후원금 신청
	@Transactional
	public void saveSponApp(FundSponAppRequestDto dto) {
		FundEntity entity = new FundEntity();
		
		entity.setMemberNum(dto.getMemberNum());
		entity.setFundSponsor(dto.getFundSponsor());
		entity.setFundPhone(dto.getFundPhone());
		entity.setFundBirth(dto.getFundBirth());
		
		entity.setFundCheck(dto.getFundCheck());
		entity.setFundMoney(dto.getFundMoney());
		entity.setFundNote(dto.getFundNote());
	}
	//후원물품 신청
	@Transactional
	public void saveItemApp(FundItemAppRequestDto dto) {
		FundEntity entity = new FundEntity();
		
		entity.setMemberNum(dto.getMemberNum());
		entity.setFundSponsor(dto.getFundSponsor());
		entity.setFundPhone(dto.getFundPhone());
		entity.setFundBirth(dto.getFundBirth());
		
		entity.setFundCheck(dto.getFundCheck());
		entity.setFundItem(dto.getFundItem());
		entity.setFundNote(dto.getFundNote());
	}
	
	//정기 후원 신청
	@Transactional
	public void saveRegularSponApp(FundRegularSponAppRequestDto dto) {
		FundEntity entity = new FundEntity();
		
		entity.setMemberNum(dto.getMemberNum());
		entity.setFundSponsor(dto.getFundSponsor());
		entity.setFundPhone(dto.getFundPhone());
		entity.setFundBirth(dto.getFundBirth());
		
		entity.setFundCheck(dto.getFundCheck());
		entity.setFundMoney(dto.getFundMoney());
		entity.setFundBank(dto.getFundBank());
		entity.setFundAccountNum(dto.getFundAccountNum());
		
		entity.setFundDepositor(dto.getFundDepositor());
		entity.setFundDrawalDate(dto.getFundDrawalDate());
		
		
	}
	
	//회원 후원 내역 조회
	@Transactional(readOnly = true)
	public List<FundEntity> getFundListByMember(String memberId, String fundPhone){
		return fundRepository.findByMemberNum_MemberIdAndFundPhone(memberId, fundPhone);
	}
	
	//비회원 후원 내역 조회
	@Transactional(readOnly = true)
	public List<FundEntity> getFundListByGuest(String fundSponsor, String fundPhone){
		return fundRepository.findByFundSponsorAndFundPhone(fundSponsor, fundPhone);
	}
	
	//회원(개별) 총 후원금 계산
	@Transactional(readOnly = true)
	public Integer getTotalFundMoneyByMember(String memberId) {
		List<FundEntity> fundList = fundRepository.findByMemberNum_MemberIdAndFundPhone(memberId, null);
				return fundList.stream()
						.mapToInt(spon -> spon.getFundMoney() != null ? spon.getFundMoney() : 0)
						.sum();
		
	}
	//전체 총 후원금 계산
	@Transactional(readOnly = true)
	public Integer getTotalFundMoneyAll() {
		return fundRepository.findAll().stream()
				.mapToInt(spon -> spon.getFundMoney() != null ? spon.getFundMoney() : 0)
				.sum();
	}
	
}
