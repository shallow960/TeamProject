package com.project.fund.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.fund.entity.FundCheck;
import com.project.fund.entity.FundEntity;

public interface FundRepository extends JpaRepository<FundEntity,Integer>{

	
	@Query(value = "SELECT f1.* FROM fund f1" + 
				   "INNER JOIN(" +
				   "	SELECT fund_sponsor, MAX)
	List<FundEntity> findLastedFundsCroupedByMember();
	
	List<FundEntity> findSponsorAndFundCheck(String fundSponsor, FundCheck fundCheck);
	
	
}
