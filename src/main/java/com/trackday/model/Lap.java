package com.trackday.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "laps")
public class Lap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long lapTimeMillis;
    private String status; // "IN_PROGRESS", "COMPLETED"

    // Default constructor
    public Lap() {}

    public void setSession(Session session) {
        this.session = session;
    }
    public Session getSession() {
        return session;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setLapTimeMillis(Long lapTimeMillis) {
        this.lapTimeMillis = lapTimeMillis;
    }
    public Long getLapTimeMillis() {
        return lapTimeMillis;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}