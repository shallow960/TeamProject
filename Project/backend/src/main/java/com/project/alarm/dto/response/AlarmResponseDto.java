package com.project.alarm.dto.response;

import java.sql.Timestamp;

import com.project.alarm.entity.AlarmCheck;

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
public class AlarmResponseDto {

    
    private String alarmTitle;
    private String alarmContent;
    private String alarmURL;
    private AlarmCheck alarmCheck;
    private Timestamp alarmTime;
}
