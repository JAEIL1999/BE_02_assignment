package com.example.be_assignment_02.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
@Getter @Setter
@AllArgsConstructor
// 전체 조회 DTO
public class ScheduleQueryConditionDto {
    private String writer;
    private String updatedDate;  // YYYY-MM-DD

}
