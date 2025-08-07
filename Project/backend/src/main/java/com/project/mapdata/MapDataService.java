package com.project.mapdata;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MapDataService {

    private final MapDataRepository mapDataRepository;

    public void saveMapData(MapDataEntity entity) {
        mapDataRepository.save(entity);
    }

    public List<MapDataEntity> getAllMapData() {
        return mapDataRepository.findAll();
    }

    public MapDataEntity getMapDataById(Integer id) {
        return mapDataRepository.findById(id)
                .orElseThrow(() -> new ChatException("지도 번호 " + id + "를 찾을 수 없습니다."));
    }

    public void deleteMapDataById(Integer id) {
        if(!mapDataRepository.existsById(id)) {
            throw new ChatException("지도 번호 " + id + "를 찾을 수 없습니다.");
        }
        mapDataRepository.deleteById(id);
    }
}
