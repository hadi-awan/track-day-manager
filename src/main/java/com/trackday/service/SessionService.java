package com.trackday.service;

import com.trackday.model.Session;
import com.trackday.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session startSession(Session session) {
        session.setStartTime(LocalDateTime.now());
        session.setStatus("ACTIVE");
        return sessionRepository.save(session);
    }

    public Session endSession(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        session.setEndTime(LocalDateTime.now());
        session.setStatus("COMPLETED");
        return sessionRepository.save(session);
    }

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public List<Session> getUserSessions(Long userId) {
        return sessionRepository.findByUserId(userId);
    }

    public Session getSessionById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
    }

    public Session updateSession(Long id, Session session) {
        Session existingSession = getSessionById(id);
        existingSession.setStartTime(session.getStartTime());
        existingSession.setEndTime(session.getEndTime());
        existingSession.setStatus(session.getStatus());
        return sessionRepository.save(existingSession);
    }

    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    public Long getCurrentSessionId() {
        // Replace `1L` with logic to get the currently logged-in user's ID (e.g., via Spring Security)
        Long currentUserId = 1L;

        return sessionRepository.findByUserIdAndStatus(currentUserId, "ACTIVE")
                .map(Session::getId)
                .orElseThrow(() -> new RuntimeException("No active session found for the user"));
    }
}
