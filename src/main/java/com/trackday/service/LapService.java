package com.trackday.service;

import com.trackday.model.Lap;
import com.trackday.model.Session;
import com.trackday.repository.LapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LapService {
    private final LapRepository lapRepository;
    private final SessionService sessionService;

    @Autowired
    public LapService(LapRepository lapRepository, SessionService sessionService) {
        this.lapRepository = lapRepository;
        this.sessionService = sessionService;
    }


    public Lap startLap(Long sessionId) {
        Session session = sessionService.getSessionById(sessionId);
        Lap lap = new Lap();
        lap.setSession(session);
        lap.setStartTime(LocalDateTime.now());
        lap.setStatus("IN_PROGRESS");
        return lapRepository.save(lap);
    }

    public Lap endLap(Long lapId) {
        Lap lap = lapRepository.findById(lapId)
                .orElseThrow(() -> new RuntimeException("Lap not found"));
        lap.setEndTime(LocalDateTime.now());
        lap.setLapTimeMillis(ChronoUnit.MILLIS.between(lap.getStartTime(), lap.getEndTime()));
        lap.setStatus("COMPLETED");
        return lapRepository.save(lap);
    }

    public List<Lap> getSessionLaps(Long sessionId) {
        return lapRepository.findBySessionId(sessionId);
    }

    public Lap updateLap(Long id, Lap lap) {
        Lap existingLap = lapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lap not found"));
        existingLap.setLapTimeMillis(lap.getLapTimeMillis());
        existingLap.setStatus(lap.getStatus());
        return lapRepository.save(existingLap);
    }

    public void deleteLap(Long id) {
        lapRepository.deleteById(id);
    }

    public Lap createLap(Lap lap) {
        Session session = sessionService.getSessionById(lap.getSession().getId());
        Lap newLap = new Lap();
        newLap.setSession(session);
        newLap.setStartTime(lap.getStartTime());
        newLap.setEndTime(lap.getEndTime());
        newLap.setLapTimeMillis(lap.getLapTimeMillis());
        newLap.setStatus(lap.getStatus());
        return lapRepository.save(newLap);
    }

    public Lap getLapById(Long id) {
        return lapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lap not found with id: " + id));
    }

    public List<Lap> getAllLaps() {
        return lapRepository.findAll();
    }
}

