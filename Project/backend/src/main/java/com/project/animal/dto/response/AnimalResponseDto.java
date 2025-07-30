package com.project.animal.dto.response;

import java.time.LocalDate;

import com.project.adopt.dto.response.AdoptIndexResponseDto;
import com.project.adopt.entity.AdoptState;
import com.project.animal.entity.AnimalSex;
import com.project.member.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalResponseDto {
	
	private String animalName;
	private String animalBreed;
	private AnimalSex animalSex;
	private LocalDate animalDate;
	private String animalContent;
	private LocalDate adoptDate;
	

}
