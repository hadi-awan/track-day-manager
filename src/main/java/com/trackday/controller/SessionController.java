package com.trackday.controller;

import com.trackday.model.Session;
import com.trackday.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {
    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<?> startSession(@RequestBody Session session) {
        return ResponseEntity.ok(sessionService.startSession(session));
    }

    @PostMapping("/{id}/end")
    public ResponseEntity<Session> endSession(@PathVariable Long id) {
        return ResponseEntity.ok(sessionService.endSession(id));
    }

    @GetMapping
    public ResponseEntity<List<Session>> getSessions() {
        return ResponseEntity.ok(sessionService.getAllSessions());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Session>> getUserSessions(@PathVariable Long userId) {
        return ResponseEntity.ok(sessionService.getUserSessions(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Session> getSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(sessionService.getSessionById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Session> updateSession(@PathVariable Long id, @RequestBody Session session) {
        return ResponseEntity.ok(sessionService.updateSession(id, session));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}
