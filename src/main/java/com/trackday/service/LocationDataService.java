package com.trackday.service;

import com.trackday.model.LocationData;
import com.trackday.repository.LocationDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LocationDataService {
    private final LocationDataRepository locationDataRepository;
    private final SessionService sessionService;

    @Autowired
    public LocationDataService(LocationDataRepository locationDataRepository, SessionService sessionService) {
        this.locationDataRepository = locationDataRepository;
        this.sessionService = sessionService;
    }

    public LocationData recordLocation(LocationData locationData) {
        return locationDataRepository.save(locationData);
    }

    public LocationData getLocationById(Long id) {
        return locationDataRepository.findById(id).orElse(null);
    }

    public List<LocationData> getAllLocations() {
        return locationDataRepository.findAll();
    }

    public List<LocationData> getSessionLocations(Long sessionId) {
        return locationDataRepository.findBySessionIdOrderByTimestampDesc(sessionId);
    }

    public LocationData updateLocation(Long id, LocationData locationData) {
        LocationData existingLocation = getLocationById(id);
        existingLocation.setLatitude(locationData.getLatitude());
        existingLocation.setLongitude(locationData.getLongitude());
        existingLocation.setSpeed(locationData.getSpeed());
        existingLocation.setTimestamp(locationData.getTimestamp());
        return locationDataRepository.save(existingLocation);
    }

    public void deleteLocation(Long id) {
        locationDataRepository.deleteById(id);
    }

    public List<LocationData> getLocationsBetweenTimestamps(LocalDateTime startTime, LocalDateTime endTime) {
        return locationDataRepository.findBySessionIdAndTimestampBetween(
                getCurrentSessionId(), startTime, endTime);
    }

    private Long getCurrentSessionId() {
        return sessionService.getCurrentSessionId();
    }
}
