package com.project.fund.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.chat.ChatCheck;
import com.project.fund.dto.request.FundItemRequestDto;
import com.project.fund.dto.request.FundMoneyRequestDto;
import com.project.fund.dto.response.FundItemResponseDto;
import com.project.fund.entity.FundCheck;
import com.project.fund.entity.FundEntity;
import com.project.fund.repository.FundRepository;

import jakarta.transaction.Transactional;

@Service

public class FundService {

	private final FundRepository fundRepository;
	
	public FundService(FundRepository fundRepository) {
	    this.fundRepository = fundRepository;
	}
	
	// 후원 물품 신청
	@Transactional
	public void saveItemFund(FundItemRequestDto dto) {
	    FundEntity entity = new FundEntity();

	    // FundSponsor는 DTO에서 받아오므로 그대로 사용
	    entity.setFundSponsor(dto.getFundSponsor());
	    entity.setMemberPhone(dto.getMemberPhone());
	    entity.setMemberBirth(dto.getMemberBirth());

	    entity.setFundItem(dto.getFundItem());
	    entity.setFundNote(dto.getFundNote());

	    // fundCheck는 String으로 들어오므로 Enum 변환 처리
	    entity.setFundCheck("Y".equalsIgnoreCase(dto.getFundCheck()) ? FundCheck.Y : FundCheck.N);

	    // 후원 시간은 현재 시각으로 세팅
	    entity.setFundTime(new java.sql.Date(System.currentTimeMillis()));

	    fundRepository.save(entity);
	}


	
	// 후원 물품 신청 내역 조회
	
	@Transactional(readOnly = true)
	public List<FundItemResponseDto> getFundItemBySponsor(String fundSponsor){
	    List<FundEntity> fundEntities = fundRepository.findByFundSponsorAndFundCheck(fundSponsor, FundCheck.Y);

	    List<FundItemResponseDto> dtos = new ArrayList<>();
	    
	    for (FundEntity entity : fundEntities) {
	        FundItemResponseDto dto = new FundItemResponseDto();
	        dto.setFundSponsor(entity.getFundSponsor());
	        dto.setMemberPhone(entity.getMemberPhone());
	        dto.setMemberBirth(entity.getMemberBirth());
	        dto.setFundCheck(entity.getFundCheck().name());
	        dto.setFundItem(entity.getFundItem());
	        dto.setFundNote(entity.getFundNote());

	        dtos.add(dto);
	    }
	    return dtos;
	}

	
	// 후원금 신청
	@Transactional
	public void saveMoneyFund(FundMoneyRequestDto dto) {
	    FundEntity entity = new FundEntity();

	    entity.setFundSponsor(dto.getFundSponsor());
	    entity.setMemberPhone(dto.getMemberPhone());
	    entity.setMemberBirth(dto.getMemberBirth());

	    // fundMoney는 Integer → String 변환 필요 (Entity 기준)
	    entity.setFundMoney(String.valueOf(dto.getFundMoney()));
	    entity.setFundNote(dto.getFundNote());

	    entity.setFundCheck("Y".equalsIgnoreCase(dto.getFundCheck()) ? FundCheck.Y : FundCheck.N);

	    entity.setFundTime(new java.sql.Date(System.currentTimeMillis()));

	    fundRepository.save(entity);
	}

	
	// 후원금 신청 내역 조회
	@Transactional(readOnly = true)
	public List<FundMoneyRequestDto> getFundMoneyBySponsor(){
		List<FundEntity> fundEntities = fundRepository.findSponsorAndFundCheck();
		
		List<FundMoneyRequestDto> dtos = new ArrayList<>();
		
		for (FundEntity entity : fundEntities) {
			FundMoneyRequestDto dto = new FundMoneyRequestDto(entity);
			
			dtos.add(dto);
		}
		return dtos;
	}
	// 후원 확인 상태 (Y)로 변경
	@Transactional
    public void markFundsAsRead(FundMarkReadRequestDto requestDto) {
        // 요청 DTO에서 관리번호 읽어오기
        String fundSponsor = requestDto.getFundSponsor();

        // DB에서 해당 관리번호에 대해 읽지 않은 채팅(N) 모두 조회
        List<FundEntity> unreadFunds = fundRepository.findByfundSponsorAndFundCheck(fundSponsor,FundCheck.N);

        // 읽지 않은 채팅 하나씩 읽음(Y) 상태로 변경
        for (FundEntity fund : unreadFunds) {
            fund.setFundCheck(FundCheck.Y);

            // 변경된 엔티티 저장 (DB 업데이트)
            fundRepository.save(fund);
        }
    }
}
