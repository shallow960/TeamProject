package com.project.adopt.dto;

import com.project.adopt.entity.Adopt;
import com.project.member.Member;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdoptRequestDto {

    private Long memberNum;     // 회원번호 (FK)
    private Long animalId;      // 입양할 동물 ID
    private LocalDate consultDate; // 상담 신청일

    public Adopt toEntity(Member member) {
        return Adopt.builder()
                .member(member)
                .animalId(animalId)
                .consultDate(consultDate)
                .adoptSts(com.project.adopt.entity.AdoptSts.ING) // 신청 시 기본은 ING 상태
                .build();
    }
}