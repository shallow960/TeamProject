package com.project.land.dto;

import com.project.land.entity.Land;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LandRequestDto {

    private Long reserveCode;
    private int animalNumber;
    private int payNumber;
    
    //dto -> entity 변환
    public Land toEntity() {
        return Land.builder()
                .reserveCode(reserveCode) 
                .animalNumber(animalNumber)
                .payNumber(payNumber)
                .build();
    }
    //사용자가 놀이터예약 페이지에 들어가서 놀이터 선택하고 ex)대형견놀이터, 소형견놀이터
    //방문일, 인원등을 입력하는 순간 reseve 테이블에 예약이 저장됨
    //뒤에 부가적인 마리 수, 금액 정보를 입력하면 이 정보가 이 시점에 land 테이블에 저장
}
