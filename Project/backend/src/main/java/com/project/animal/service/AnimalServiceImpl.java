package com.project.animal.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.animal.dto.request.AnimalRequestDto;
import com.project.animal.dto.response.AnimalResponseDto;
import com.project.animal.entity.AnimalEntity;
import com.project.animal.repository.AnimalRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService{
	
	private final AnimalRepository animalRepository;
	
	@Override //입력
	public Long register(AnimalRequestDto dto) {
		AnimalEntity entity = AnimalEntity.builder()
				.animalName(dto.getAnimalName())
				.animalBreed(dto.getAnimalBreed())
				.animalSex(dto.getAnimalSex())
				.animalDate(dto.getAnimalDate())
				.animalContent(dto.getAnimalContent())
				.adoptDate(dto.getAdoptDate())
				.build();
		
		animalRepository.save(entity);
		return entity.getAnimalId();
				
	}
	
	@Override // 수정
	public void modify(Long animalId, AnimalRequestDto dto) {
		AnimalEntity entity = animalRepository.findById(animalId)
				.orElseThrow(() -> new IllegalArgumentException("수정할 동물이 존재하지 않습니다."));
				
				entity.setAnimalName(dto.getAnimalName());
				entity.setAnimalBreed(dto.getAnimalBreed());
				entity.setAnimalSex(dto.getAnimalSex());
				entity.setAnimalDate(dto.getAnimalDate());
				entity.setAnimalContent(dto.getAnimalContent());
				entity.setAdoptDate(dto.getAdoptDate());
				
						
	}
	
	@Override //조회
	public AnimalResponseDto get(Long animalId) {
		   AnimalEntity entity = animalRepository.findById(animalId)
			        .orElseThrow(() -> new IllegalArgumentException("해당 동물이 존재하지 않습니다."));
		   
		   return AnimalResponseDto.builder()
				.animalName(entity.getAnimalName())
				.animalBreed(entity.getAnimalBreed())
				.animalSex(entity.getAnimalSex())
				.animalDate(entity.getAnimalDate())
				.animalContent(entity.getAnimalContent())
				.adoptDate(entity.getAdoptDate())
				.build();
		
	}
	
	@Override //삭제
	public void remove(Long animalId) {
		if(!animalRepository.existsById(animalId)) {
			throw new IllegalArgumentException("삭제할 동물이 존재하지 않습니다.");
		}
		animalRepository.deleteById(animalId);
	}
	
	


}
