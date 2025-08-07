package com.project.mapdata;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mapdata")
@RequiredArgsConstructor
public class MapDataController {

    private final MapDataService mapDataService;

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<MapDataEntity>> getAll() {
        List<MapDataEntity> list = mapDataService.getAllMapData();
        return ResponseEntity.ok(list);
    }

    // ID로 조회
    @GetMapping("/{id}")
    public ResponseEntity<MapDataEntity> getById(@PathVariable Integer id) {
        MapDataEntity entity = mapDataService.getMapDataById(id);
        return ResponseEntity.ok(entity);
    }

    // 새 데이터 등록
    @PostMapping
    public ResponseEntity<MapDataEntity> create(@RequestBody MapDataEntity entity) {
        mapDataService.saveMapData(entity);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    // ID로 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        mapDataService.deleteMapDataById(id);
        return ResponseEntity.noContent().build();
    }
}
