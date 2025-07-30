package com.project.animal.dto.request;

import java.time.LocalDate;

import com.project.animal.entity.AnimalSex;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalRequestDto {

	private String animalName;
	private String animalBreed;
	private AnimalSex animalSex;
	private LocalDate animalDate;
	private String animalContent;
	private LocalDate adoptDate;
	
}
