package com.trackday.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "track_id")
    private Track track;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;

    public Session() {}

    // Getters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public Track getTrack() { return track; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public String getStatus() { return status; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setTrack(Track track) { this.track = track; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public void setStatus(String status) { this.status = status; }
}
