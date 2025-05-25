package com.example.be_assignment_02.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Schedule {
    private int id;
    private String todo;
    private String writer;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}