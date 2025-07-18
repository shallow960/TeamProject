package com.project.admin;

import java.time.LocalDate;
import lombok.Data;

@Data
public class AdminReservationSearchDto {
	private LocalDate startDate; // 검색 시작일
    private LocalDate endDate;   // 검색 종료일
    private String reserveStatus;
    private String memberName;
}
