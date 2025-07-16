package com.project.fund;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FundService {

	private final FundRepository fundRepository;
	
	public void saveFund(FundEntity entity) {
		fundRepository.save(entity);
	}
	
	public List<FundEntity> getAllFund(){
		return fundRepository.findAll();
	}
	
	
	
	
	
	//예외처리
	public FundEntity getFundById(Integer id) {
		return fundRepository.findById(id)
				.orElseThrow(() -> new FundException("회원 ID " + id + "를 찾을 수 없습니다."));
	}
}
