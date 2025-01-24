package com.trackday.service;

import com.trackday.model.Track;
import com.trackday.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackService {
    private final TrackRepository trackRepository;

    @Autowired
    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public Track createTrack(Track track) {
        return trackRepository.save(track);
    }

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }
}

