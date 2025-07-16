package com.project.adopt.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.project.member.Member;

@Entity
@Table(name = "Adopt")
@Getter 
@Setter 
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Adopt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adopt_num")
    private Long adoptNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_num", nullable = false)
    private Member member;

    @Column(name = "animal_id")
    private Long animalId;

    @Column(name = "consult_dt")
    private LocalDate consultDate;

    @Column(name = "vist_dt")
    private LocalDateTime visitDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "adopt_sts")
    private AdoptSts adoptSts;
}