package com.project.fund.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.fund.entity.FundEntity;

public interface FundRepository extends JpaRepository<FundEntity, Integer>{
	
	//회원 후원 내역 조회
	List<FundEntity> findByMemberNum_MemberIdAndFundPhone(String memberId, String fundPhone);
	
	//비회원 후원 내역 조회
	List<FundEntity> findByFundSponsorAndFundPhone(String FundSponsor, String FundPhone);


	
}
