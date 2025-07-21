package com.project.fund.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.fund.entity.FundCheck;
import com.project.fund.entity.FundEntity;

public interface FundRepository extends JpaRepository<FundEntity,Integer>{

	//후원자별 최근 내역 리스트 조회
	@Query("SELECT f FROM FundEntity f WHERE f.fundCheck = 'N' GROUP BY f.fundSponsor")
	List<FundEntity> findFundsGroupedBySponsor();
	
	// 후원금 내역 조회
	List<FundEntity> findByFundSponsorAndFundCheck(String fundSponsor, FundCheck fundCheck);

	
	// 특정 후원자의 후원 필 여부 조회
	List<FundEntity> findSponsorAndFundCheck(String fundSponsor, FundCheck fundCheck);
	
	
	
}
