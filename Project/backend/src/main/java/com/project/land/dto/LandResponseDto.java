package com.project.land.dto;

import com.project.reserve.entity.ReserveState;
import com.project.land.entity.Land;
import com.project.reserve.entity.Reserve;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LandResponseDto {

    // Land 정보
    private Long reserveCode;
    private int animalNumber;
    private int payNumber;

    // Reserve 정보 포함
    private LocalDate reserveDate;     // 신청일
    private LocalDate visitDate;       // 예약일
    private String reserveName;        // 예약명
    private ReserveState reserveState;  // 예약 상태

    public static LandResponseDto from(Land land, Reserve reserve) {
        return LandResponseDto.builder()
                .reserveCode(land.getReserveCode())
                .animalNumber(land.getAnimalNumber())
                .payNumber(land.getPayNumber())
                .reserveDate(reserve.getReserveDate())
                .visitDate(reserve.getClosedDate()) // 또는 reserve.getVisitDate()
                .reserveName(convertReserveType(reserve.getReserveType()))
                .reserveState(reserve.getReserveState())
                .build();
    }

    private static String convertReserveType(int typeCode) {
        return switch (typeCode) {
            case 1 -> "놀이터 예약(소형견)";
            case 2 -> "놀이터 예약(중/대형견)";
            default -> "놀이터 예약";
        };
    }
}