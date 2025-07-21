package com.project.fund.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.fund.dto.request.FundItemRequestDto;
import com.project.fund.dto.request.FundMoneyRequestDto;
import com.project.fund.dto.response.FundItemResponseDto;
import com.project.fund.service.FundService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fund")
public class FundController {

    private final FundService fundService;

    // ✅ [1] 후원자가 후원 물품 신청
    @PostMapping("/item/{fundSponsor}/sign")
    public ResponseEntity<?> signFundItem(
            @PathVariable String fundSponsor,
            @Valid @RequestBody FundItemRequestDto requestDto) {

        fundService.saveItemFund(fundSponsor, requestDto); // 저장 메서드 호출
        return ResponseEntity.ok("후원 물품 신청 완료");
    }

    // ✅ [2] 후원자 후원 물품 신청 내역 조회
    @GetMapping("/item/{fundSponsor}")
    public ResponseEntity<List<FundItemResponseDto>> getFundItem(@PathVariable String fundSponsor) {
        List<FundItemResponseDto> fundList = fundService.getFundItemBySponsor(fundSponsor);
        return ResponseEntity.ok(fundList);
    }

    // ✅ [3] 후원자가 후원금 신청
    @PostMapping("/money/{fundSponsor}/sign")
    public ResponseEntity<?> signFundMoney(
            @PathVariable String fundSponsor,
            @Valid @RequestBody FundMoneyRequestDto requestDto) {

        fundService.saveMoneyFund(fundSponsor, requestDto);
        return ResponseEntity.ok("후원금 신청 완료");
    }

    // ✅ [4] 후원자 후원금 신청 내역 조회
    @GetMapping("/money/{fundSponsor}")
    public ResponseEntity<List<FundMoneyRequestDto>> getFundMoney(@PathVariable String fundSponsor) {
        List<FundMoneyRequestDto> fundList = fundService.getFundMoneyBySponsor(fundSponsor);
        return ResponseEntity.ok(fundList);
    }
}
