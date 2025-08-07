package com.project.chat.entity;

import java.sql.Timestamp;

import com.project.admin.AdminEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_num")
	private Integer memberNum; //회원번호
	
	@Column(name = "manage_num", nullable =false)
	private Integer manageNum; //관리번호
	
	@ManyToOne
    @JoinColumn(name = "admin_id", nullable=false) // 참조할 테이블의 PK 컬럼명.
    private AdminEntity adminId; //관리자 아이디
	
	@Column(name = "chat_cont", nullable=false)
	private String chatCont; // 대화 내용
	
	@Column(name = "send_time",nullable=false)
	private Timestamp sendTime; // 보낸 시간
	
	@Column(name = "take_time", nullable=false)
	private Timestamp takeTime; // 받은 시간

	@Enumerated(EnumType.STRING)
	@Column(name = "chat_check", length = 1, nullable=false)
	private ChatCheck chatCheck;  //확인 상태


}
