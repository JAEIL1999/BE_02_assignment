package com.example.be_assignment_02.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
//response DTO : 비밀번호 제외
public class ScheduleResponseDto {
    private int id;
    private String todo;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 생성자: Entity → DTO 변환
    public ScheduleResponseDto(int id, String todo, String writer, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.todo = todo;
        this.writer = writer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
