package com.project.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
//로그인 완료 후 front-end 에 제공할 변수 미리 선언
public class MemberLoginResponseDto {
    private String memberId; //아이디
    private String memberName; //회원이름
    private String message; //내용
    private String authority; //프론트에서 사용할 권한체크(사용자, 관리자)
    private String accessToken; // JWT 또는 세션 기반이라면 포함
}