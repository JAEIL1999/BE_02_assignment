package com.example.be_assignment_02.repository;

import com.example.be_assignment_02.model.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleRepository {
    private final String url = "jdbc:mysql://localhost:3306/assignment";
    private final String username = "jaeil";
    private final String password = "qkrwodlf12";

    // 일정 생성
    public int save(Schedule schedule) throws SQLException {
        String sql = "INSERT INTO schedule (todo, writer, password, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, schedule.getTodo());
            stmt.setString(2, schedule.getWriter());
            stmt.setString(3, schedule.getPassword());
            stmt.setTimestamp(4, Timestamp.valueOf(schedule.getCreatedAt()));
            stmt.setTimestamp(5, Timestamp.valueOf(schedule.getUpdatedAt()));
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        }
    }

    // 전체 조회 (조건 필터링)
    public List<Schedule> findAll(String writer, String updatedDate) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule WHERE 1=1");
        if (writer != null && !writer.isEmpty()) sql.append(" AND writer = ?");
        if (updatedDate != null && !updatedDate.isEmpty()) sql.append(" AND DATE(updated_at) = ?");
        sql.append(" ORDER BY updated_at DESC"); // 수정일 기준 내림차순 정렬

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int index = 1;
            if (writer != null && !writer.isEmpty()) stmt.setString(index++, writer);
            if (updatedDate != null && !updatedDate.isEmpty()) stmt.setString(index, updatedDate);
            ResultSet rs = stmt.executeQuery();
            List<Schedule> list = new ArrayList<>();
            while (rs.next()) {
                list.add(toSchedule(rs));
            }
            return list;
        }
    }

    // id에 대해서 조회
    public Schedule findById(int id) throws SQLException {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return toSchedule(rs);
            }
            return null;
        }
    }

    // 일정 수정 (비밀번호는 확인하지 않음, Service 계층에서 확인)
    public boolean updateSchedule(int id, String todo, String writer, LocalDateTime updatedAt) throws SQLException {
        String sql = "UPDATE schedule SET todo = ?, writer = ?, updated_at = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, todo);
            stmt.setString(2, writer);
            stmt.setTimestamp(3, Timestamp.valueOf(updatedAt));
            stmt.setInt(4, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // 일정 삭제 (비밀번호는 확인하지 않음, Service 계층에서 확인)
    public boolean deleteSchedule(int id) throws SQLException {
        String sql = "DELETE FROM schedule WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }



    private Schedule toSchedule(ResultSet rs) throws SQLException {
        return new Schedule(
                rs.getInt("id"),
                rs.getString("todo"),
                rs.getString("writer"),
                rs.getString("password"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}