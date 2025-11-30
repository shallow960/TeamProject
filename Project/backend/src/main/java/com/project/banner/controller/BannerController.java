package com.project.banner.controller;

import com.project.banner.dto.BannerRequestDto;
import com.project.banner.dto.BannerResponseDto;
import com.project.banner.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
// context-path 가 이미 /api 이므로 여기서는 /banner 만 사용
@RequestMapping("/banner")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    // 배너 생성
    @PostMapping
    public void createBanner(
            @RequestPart("data") BannerRequestDto dto,
            @RequestPart("file") MultipartFile file
    ) throws IOException {
        bannerService.createBanner(dto, file);
    }

    // 배너 전체 목록 조회 (디버그용)
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            System.out.println("[DEBUG] GET /api/banner 전체 목록 호출");
            List<BannerResponseDto> list = bannerService.getAll();
            System.out.println("[DEBUG] GET /api/banner 정상 응답, size=" + (list != null ? list.size() : -1));
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace(); // stdout 로그 확인용

            Map<String, Object> body = new HashMap<>();
            body.put("debug", true);
            body.put("errorClass", e.getClass().getName());
            body.put("message", e.getMessage());

            StackTraceElement[] stack = e.getStackTrace();
            if (stack != null && stack.length > 0) {
                body.put("at", stack[0].toString()); // 가장 위의 스택 한 줄
            }

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(body);
        }
    }

    // 특정 배너 상세 정보 조회
    @GetMapping("/{id}")
    public BannerResponseDto getDetail(@PathVariable Long id) {
        return bannerService.getDetail(id);
    }

    // 배너 수정
    @PutMapping("/{id}")
    public void updateBanner(
            @PathVariable Long id,
            @RequestPart("data") BannerRequestDto dto,
            @RequestPart(name = "file", required = false) MultipartFile file
    ) throws IOException {
        bannerService.update(id, dto, file);
    }   

    // 배너 단건 삭제
    @DeleteMapping("/{id}")
    public void deleteBanner(@PathVariable Long id) {
        bannerService.delete(id);
    }

    // 배너 복수 삭제
    @PostMapping("/delete-bulk")
    public void deleteBulk(@RequestBody List<Long> bannerIds) {
        if (bannerIds == null || bannerIds.isEmpty()) return;
        bannerService.deleteBulk(bannerIds);
    }

    //25.11.30 수정
    // 활성 상태 배너 조회 (디버그용)
    @GetMapping("/active")
    public ResponseEntity<?> getActiveBanners() {
        try {
            System.out.println("[DEBUG] GET /api/banner/active 호출");
            List<BannerResponseDto> list = bannerService.getActiveBanners();
            System.out.println("[DEBUG] GET /api/banner/active 정상 응답, size=" + (list != null ? list.size() : -1));
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();

            Map<String, Object> body = new HashMap<>();
            body.put("debug", true);
            body.put("errorClass", e.getClass().getName());
            body.put("message", e.getMessage());

            StackTraceElement[] stack = e.getStackTrace();
            if (stack != null && stack.length > 0) {
                body.put("at", stack[0].toString());
            }

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(body);
        }
    }
}
