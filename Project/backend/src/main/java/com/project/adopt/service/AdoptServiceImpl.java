package com.project.adopt.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.adopt.dto.request.AdoptScheduleRequestDto;
import com.project.adopt.dto.response.AdoptIndexResponseDto;
import com.project.adopt.entity.AdoptEntity;
import com.project.adopt.repository.AdoptRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdoptServiceImpl implements AdoptService {

    private final AdoptRepository adoptRepository;

    @Override // 입력
    public Long register(AdoptScheduleRequestDto dto) {
        AdoptEntity entity = AdoptEntity.builder()
            .adoptTitle(dto.getAdoptTitle())
            .visitDate(dto.getVisitDate())
            .member(dto.getMemberName()) 
            .animalId(dto.getAnimalId())
            .adoptContent(dto.getAdoptContent())
            .build();

        adoptRepository.save(entity);
        return entity.getAdoptNum();
    }

    @Override // 조회
    public AdoptIndexResponseDto get(Long adoptNum) {
        AdoptEntity entity = adoptRepository.findById(adoptNum)
		        .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

           
        return AdoptIndexResponseDto.builder()
            .adoptNum(entity.getAdoptNum())
            .visitDate(entity.getVisitDate())
            .memberName(entity.getMember())
            .animalId(entity.getAnimalId())
            .adoptState(entity.getAdoptState())
            .adoptContent(entity.getAdoptContent())
            .build();
    }

    @Override // 수정
    public void modify(Long adoptNum, AdoptScheduleRequestDto dto) {
        AdoptEntity entity = adoptRepository.findById(adoptNum)
        		.orElseThrow(()-> new IllegalArgumentException("수정할 일정이 존재하지 않습니다"));

        entity.setAdoptTitle(dto.getAdoptTitle());
        entity.setVisitDate(dto.getVisitDate());
        entity.setMember(dto.getMemberName());
        entity.setAnimalId(dto.getAnimalId());
        entity.setAdoptContent(dto.getAdoptContent());

    }

    @Override // 삭제
    public void remove(Long adoptNum) {
        if (!adoptRepository.existsById(adoptNum)) {
            throw new IllegalArgumentException("삭제할 일정이 존재하지 않습니다.");
        }
        adoptRepository.deleteById(adoptNum);
    }
}
