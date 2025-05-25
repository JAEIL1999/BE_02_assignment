package com.example.be_assignment_02.controller;

import com.example.be_assignment_02.dto.*;
import com.example.be_assignment_02.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService service = new ScheduleService();

    @PostMapping
    public ResponseEntity<Integer> createSchedule(@RequestBody ScheduleRequestDto dto) throws SQLException {
        return service.createSchedule(dto);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(
            @RequestParam(required = false) String writer,
            @RequestParam(required = false) String updatedDate) throws SQLException {
        return service.getAllSchedules(new ScheduleQueryConditionDto(writer, updatedDate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable int id) throws SQLException {
        return service.getScheduleById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSchedule(@PathVariable int id, @RequestBody ScheduleRequestDto dto) throws SQLException {
        return service.updateSchedule(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable int id, @RequestParam String password) throws SQLException {
        return service.deleteSchedule(id, password);
    }

}
