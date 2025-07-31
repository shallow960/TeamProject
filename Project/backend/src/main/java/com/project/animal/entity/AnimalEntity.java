package com.project.animal.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Animal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "animal_id", nullable = false)
	private Long animalId;
	
	@Column(name = "animal_name")
	private String animalName; //이름
	
	@Column(name = "animal_breed")
	private String animalBreed; //견종
	
	@Column(name = "animal_sex")
	private AnimalSex animalSex; //성별
	
	@Column(name = "animal_date")
	private LocalDate animalDate; // 입소일
	
	@Column(name = "animal_content")
	private String animalContent; //특이사항
	
	@Column(name = "animal_state")
	private AnimalState animalState; // 상태
	
	@Column(name = "adopt_date")
	private LocalDate adoptDate; // 입양날짜
	
	
}

