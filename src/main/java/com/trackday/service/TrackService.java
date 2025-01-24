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

    public Track getTrackById(Long id) {
        return trackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Track not found"));
    }

    public Track updateTrack(Long id, Track track) {
        Track existingTrack = getTrackById(id);
        existingTrack.setName(track.getName());
        existingTrack.setLatitude(track.getLatitude());
        existingTrack.setLongitude(track.getLongitude());
        return trackRepository.save(existingTrack);
    }

    public void deleteTrack(Long id) {
        trackRepository.deleteById(id);
    }
}

