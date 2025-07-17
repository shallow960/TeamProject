package com.project.chat.Entity;

import java.sql.Timestamp;

import com.project.admin.AdminEntity;
import com.project.member.MemberEntity;

import jakarta.persistence.Entity;
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

@Entity
@Table(name = "chat_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chatRoomId;

    @ManyToOne
    @JoinColumn(name = "member_num", nullable = false)
    private MemberEntity memberNum;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private AdminEntity adminId;

    private Timestamp createdAt;
}
