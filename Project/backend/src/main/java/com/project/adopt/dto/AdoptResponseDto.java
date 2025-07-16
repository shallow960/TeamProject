package com.project.adopt.dto;

import com.project.adopt.entity.Adopt;
import com.project.adopt.entity.AdoptSts;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdoptResponseDto {

    private Long adoptNum;        // 입양번호 (PK)
    private Long memberNum;       // 회원번호 (FK)
    private Long animalId;        // 동물 ID
    private LocalDate consultDate;  // 상담 신청일
    private LocalDateTime visitDate; // 방문 예정일
    private AdoptSts adoptSts;    // 입양 진행 상태

    public static AdoptResponseDto from(Adopt adopt) {
        return AdoptResponseDto.builder()
                .adoptNum(adopt.getAdoptNum())
                .memberNum(adopt.getMember().getMemberNum())
                .animalId(adopt.getAnimalId())
                .consultDate(adopt.getConsultDate())
                .visitDate(adopt.getVisitDate())
                .adoptSts(adopt.getAdoptSts())
                .build();
    }
}
