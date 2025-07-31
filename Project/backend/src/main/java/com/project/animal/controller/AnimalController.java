package com.project.animal.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.animal.dto.request.AnimalRequestDto;
import com.project.animal.dto.response.AnimalResponseDto;
import com.project.animal.service.AnimalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class AnimalController {

	private final AnimalService animalService;
	
	//관리자가 list에서 '조회' 클릭
	@GetMapping("/")
	public AnimalResponseDto get(@PathVariable Long animalId) {
		return animalService.get(animalId);
	}
	
	
	//관리자가 '등록' 클릭
	@PostMapping("/")
	public void registerAnimal(@RequestBody AnimalRequestDto dto) {
		animalService.register(dto);
	}
	
	
	//관리자가 '수정' 클릭
	@PutMapping("/")
	public void updateAnimal(@PathVariable Long animalId,@RequestBody AnimalRequestDto dto) {
		animalService.modify(animalId, dto);
	}
	
	
	//관리자가 '삭제' 클릭
	@DeleteMapping("/")
	public void delete(@PathVariable Long animalId) {
		animalService.remove(animalId);
	}
	
	//관리자가 파일 등록
	@PostMapping("/")
	public ResponseEntity<?> registerAnimal(
			@ModelAttribute AnimalRequestDto dto,
			@RequestParam("file") MultipartFile file) throws IOException{
		
		Long id = animalService.register(dto, file);
		return ResponseEntity.ok(id);
	}
}
