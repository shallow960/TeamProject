package com.project.alarm.dto.response;

import java.sql.Timestamp;

import com.project.alarm.entity.AlarmEntity;

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

    private Long alarmId;
    private String alarmTitle;
    private String alarmContent;
    private String alarmURL;
    private String alarmCheck;
    private Timestamp alarmTime;

    public static AlarmResponseDto fromEntity(AlarmEntity entity) {
        return AlarmResponseDto.builder()
                .alarmId(entity.getAlarmId())
                .alarmTitle(entity.getAlarmTitle())
                .alarmContent(entity.getAlarmContent())
                .alarmURL(entity.getAlarmURL())
                .alarmCheck(entity.getAlarmCheck().name())
                .alarmTime(entity.getAlarmTime())
                .build();
    }
}
