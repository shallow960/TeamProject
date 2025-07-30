package com.project.adopt.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.member.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdoptScheduleRequestDto {

	private String adoptTitle;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate visitDate;
	
//	private LocalDate visitTime;
	
	private MemberEntity memberName;
	private Long animalId;
	private String adoptContent;
	
	
	
}
