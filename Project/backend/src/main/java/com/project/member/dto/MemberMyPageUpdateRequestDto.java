package com.project.member.dto;

import com.project.member.entity.MemberSex;

import lombok.Data;

@Data
//수정페이지
//사용자가 정보를 수정하고 서버에 수정요청할때 쓰는 dto
public class MemberMyPageUpdateRequestDto {

    //private Long memberId; //로그인 id(수정불가)
    //private String memberBirth; //생년월일(수정불가)
	//private String kakaoId; //카카오 계정(수정불가)
	private String memberName; //사용자 이름
	private MemberSex memberSex; //성별
	private String memberPhone; //연락처
    private String memberAddress; //주소
    private boolean smsAgree; // SNS 인증 여부
}