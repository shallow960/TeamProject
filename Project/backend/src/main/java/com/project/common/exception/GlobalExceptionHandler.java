package com.project.common.exception;

import java.time.LocalDateTime;

//import com.project.common.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	// ✅ 잘못된 요청 처리 (400)
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
	    // 필요하면 여기에도 로그
	    log.warn("IllegalArgumentException: {}", ex.getMessage());
	    return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	// ✅ 접근 거부 (403)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiErrorResponse> handleAccessDenied(AccessDeniedException ex) {
	    log.warn("AccessDeniedException: {}", ex.getMessage());
	    return buildErrorResponse(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
	}

	// ✅ 중복데이터 처리 예외 (409)
	@ExceptionHandler(DuplicateException.class)
	public ResponseEntity<ApiErrorResponse> handleDuplicateException(DuplicateException ex) {
	    log.warn("DuplicateException: {}", ex.getMessage());
	    return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
	}

	// ✅ 예기치 못한 오류 처리 (500)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleUnexpected(Exception ex) {
	    // 여기서 실제 원인 스택트레이스를 남긴다
	    log.error("Unhandled exception occurred", ex);
	    return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");
	}

	// ✅ 공통 응답 생성 메서드
	private ResponseEntity<ApiErrorResponse> buildErrorResponse(HttpStatus status, String message) {
	    ApiErrorResponse error = ApiErrorResponse.builder()
	            .status(status.value())
	            .error(status.getReasonPhrase())
	            .message(message)
	            .timestamp(LocalDateTime.now())
	            .build();

	    return ResponseEntity.status(status).body(error);
	}
}
