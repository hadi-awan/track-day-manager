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

    public List<Session> getUserSessions(Long userId) {
        return sessionRepository.findByUserId(userId);
    }
}
