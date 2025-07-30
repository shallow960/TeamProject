package com.project.adopt.dto.response;

import java.time.LocalDate;

import com.project.adopt.entity.AdoptState;
import com.project.member.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdoptIndexResponseDto {

	private Long adoptNum;
	private MemberEntity memberName;
	private LocalDate visitDate;
	private Long animalId;
	private AdoptState adoptState;
	private String adoptContent;
}
