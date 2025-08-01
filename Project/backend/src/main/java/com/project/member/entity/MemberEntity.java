package com.project.member.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.project.admin.entity.AdminEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEntity {
	
	@Id //기본키
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_num")
    private Long memberNum; //회원번호

    @Column(name = "member_id", nullable = false, length = 80)
    private String memberId; //이메일 아이디

    @Column(name = "member_pw", nullable = false, length = 255)
    private String memberPw; //비밀번호

    @Column(name = "member_name", nullable = false, length = 12)
    private String memberName; //이름

    @Column(name = "member_birth", nullable = false, length = 25)
    private LocalDate memberBirth; //생년월일

    @Column(name = "member_phone")
    private String memberPhone; //휴대폰 번호

    @Column(name = "member_address", nullable = false, length = 255)
    private String memberAddress; //주소

    @Column(name = "member_day")
    private LocalDate memberDay; //가입일시

    @Column(name = "member_lock")
    private Boolean memberLock; //계정 잠금 여부

    @Enumerated(EnumType.STRING)
    @Column(name = "member_sex")
    private MemberSex memberSex; //성별
    
    @Enumerated(EnumType.STRING) 
    @Column(name = "member_state")
    private MemberState memberState; //회원상태
    
    @Column(name = "out_date")
    private LocalDateTime outDate; // 회원이 OUT 상태로 변경된 순간 기록
    
    @Column(name = "sns_yn")
    private boolean smsAgree; //문자 수신여부(동의/비동의)

    @Column(name = "kakao_id", length = 255)
    private String kakaoId; //카카오아이디
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private AdminEntity admin;

    private String accessToken;
    private String refreshToken;
}