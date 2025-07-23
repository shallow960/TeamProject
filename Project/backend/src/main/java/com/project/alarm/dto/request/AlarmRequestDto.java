package com.project.alarm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlarmRequestDto {

    private Long memberNum;        // 연관된 회원 번호
    private String alarmTitle;     // 알림 제목
    private String alarmContent;   // 알림 내용
    private String alarmURL;       // 클릭 시 이동할 URL
}
