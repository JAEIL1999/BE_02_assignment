package com.example.be_assignment_02.service;

import com.example.be_assignment_02.dto.*;
import com.example.be_assignment_02.model.Schedule;
import com.example.be_assignment_02.repository.ScheduleRepository;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleService {
    private final ScheduleRepository repository = new ScheduleRepository();

    public ResponseEntity<Integer> createSchedule(ScheduleRequestDto dto) throws SQLException {
        Schedule schedule = new Schedule(0, dto.getTodo(), dto.getWriter(), dto.getPassword(),
                LocalDateTime.now(), LocalDateTime.now());
        int id = repository.save(schedule);
        return ResponseEntity.ok(id);
    }

    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(ScheduleQueryConditionDto condition) throws SQLException {
        List<Schedule> schedules = repository.findAll(condition.getWriter(), condition.getUpdatedDate());
        List<ScheduleResponseDto> dtoList = schedules.stream()
                .map(s -> new ScheduleResponseDto(s.getId(), s.getTodo(), s.getWriter(), s.getCreatedAt(), s.getUpdatedAt()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<ScheduleResponseDto> getScheduleById(int id) throws SQLException {
        Schedule s = repository.findById(id);
        if (s == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ScheduleResponseDto(s.getId(), s.getTodo(), s.getWriter(), s.getCreatedAt(), s.getUpdatedAt()));
    }

    public ResponseEntity<String> updateSchedule(int id, ScheduleRequestDto dto) throws SQLException {
        Schedule s = repository.findById(id);
        if (s == null) {
            return ResponseEntity.status(404).body("해당 ID의 일정이 존재하지 않습니다.");
        }
        if (!s.getPassword().equals(dto.getPassword())) {
            return ResponseEntity.status(403).body("비밀번호가 일치하지 않습니다.");
        }
        boolean success = repository.updateSchedule(id, dto.getTodo(), dto.getWriter(), LocalDateTime.now());
        if (success) {
            return ResponseEntity.ok("일정이 수정되었습니다.");
        }
        return ResponseEntity.status(500).body("일정 수정 실패.");
    }

    // 일정 삭제 (비밀번호 검증 포함)
    public ResponseEntity<String> deleteSchedule(int id, String password) throws SQLException {
        Schedule s = repository.findById(id);
        if (s == null) {
            return ResponseEntity.status(404).body("해당 ID의 일정이 존재하지 않습니다.");
        }
        if (!s.getPassword().equals(password)) {
            return ResponseEntity.status(403).body("비밀번호가 일치하지 않습니다.");
        }
        boolean success = repository.deleteSchedule(id);
        if (success) {
            return ResponseEntity.ok("일정이 삭제되었습니다.");
        }
        return ResponseEntity.status(500).body("일정 삭제 실패.");
    }

}
