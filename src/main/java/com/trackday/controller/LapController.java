package com.trackday.controller;

import com.trackday.model.Lap;
import com.trackday.service.LapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/laps")
public class LapController {
    private final LapService lapService;

    @Autowired
    public LapController(LapService lapService) {
        this.lapService = lapService;
    }

    @PostMapping
    public ResponseEntity<Lap> createLap(@RequestBody Lap lap) {
        return ResponseEntity.ok(lapService.createLap(lap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lap> getLap(@PathVariable Long id) {
        return ResponseEntity.ok(lapService.getLapById(id));
    }

    @GetMapping
    public ResponseEntity<List<Lap>> getAllLaps() {
        return ResponseEntity.ok(lapService.getAllLaps());
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<Lap>> getSessionLaps(@PathVariable Long sessionId) {
        return ResponseEntity.ok(lapService.getSessionLaps(sessionId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lap> updateLap(@PathVariable Long id, @RequestBody Lap lap) {
        return ResponseEntity.ok(lapService.updateLap(id, lap));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLap(@PathVariable Long id) {
        lapService.deleteLap(id);
        return ResponseEntity.noContent().build();
    }
}
