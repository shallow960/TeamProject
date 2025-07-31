package com.project.adopt.entity;

import java.time.LocalDate;

import com.project.member.entity.MemberEntity;

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
@Table(name = "Adopt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdoptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adopt_num", nullable = false)
    private Long adoptNum;	//입양 번호

    // 외래키: 회원
    @ManyToOne(fetch = FetchType.LAZY) //다대일 관계
    @JoinColumn(name = "member_num", nullable = false)
    private MemberEntity member;	//회원 번호

    @Column(name = "animal_id")	//동물 ID
    private Long animalId;

    @Column(name = "consult_dt")	// 상담 날짜
    private LocalDate consultDate;	

    @Column(name = "vist_dt")	// 방문 예정일
    private LocalDate visitDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "adopt_state")	// 입양 진행상태
    private AdoptState adoptState;	
    
    @Column(name = "adopt_title")	// 제목
    private String adoptTitle;
    
    @Column(name = "adopt_content") // 상담내용
    private String adoptContent;
    
    
    
}