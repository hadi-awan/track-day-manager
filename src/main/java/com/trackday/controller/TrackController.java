package com.trackday.controller;

import com.trackday.model.Track;
import com.trackday.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {
    private final TrackService trackService;

    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping
    public ResponseEntity<Track> createTrack(@RequestBody Track track) {
        return ResponseEntity.ok(trackService.createTrack(track));
    }

    @GetMapping
    public ResponseEntity<List<Track>> getAllTracks() {
        return ResponseEntity.ok(trackService.getAllTracks());
    }
}
