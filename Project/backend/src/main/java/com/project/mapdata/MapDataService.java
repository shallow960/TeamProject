package com.project.mapdata;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.entity.mapdata.MapDataEntity;
import com.project.repository.mapData.MapDataRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MapDataService {

	private final MapDataRepository mapDataRepository;
	
	public void saveMapData(MapDataEntity entity) {
		mapDataRepository.save(entity);
	}
	
	public List<MapDataEntity> getAllMapData(){
		return mapDataRepository.findAll();
	}
	
	
	
	
	
	
	
	//예외처리
	
	public MapDataEntity getMapDataById(Integer id) {
		return mapDataRepository.findById(id)
				.orElseThrow(() -> new ChatException("지도 번호 " + id + "를 찾을 수 없습니다."))
	}
	
	
}
