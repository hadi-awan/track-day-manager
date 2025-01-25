package com.trackday.controller;

import com.trackday.model.Lap;
import com.trackday.model.LocationData;
import com.trackday.model.Session;
import com.trackday.model.Track;
import com.trackday.service.LocationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.trackday.service.LapService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LocationWebSocketController {
    private final LocationDataService locationDataService;
    private final LapService lapService;
    private final Map<Long, LocationData> lastLocations = new HashMap<>();

    private static final double START_LINE_RADIUS = 0.0001; // ~10 meters radius

    @Autowired
    public LocationWebSocketController(LocationDataService locationDataService, LapService lapService) {
        this.locationDataService = locationDataService;
        this.lapService = lapService;
    }

    @MessageMapping("/location")
    @SendTo("/topic/location")
    public LocationData sendLocation(LocationData locationData) {
        return locationDataService.recordLocation(locationData);
    }

    @MessageMapping
    @SendTo("/topic/location")
    public Map<String, Object> trackLocation(LocationData locationData) {
        LocationData saved = locationDataService.recordLocation(locationData);
        Lap currentLap = checkLapCompletion(saved);

        Map<String, Object> response = new HashMap<>();
        response.put("location", saved);
        if (currentLap != null) {
            response.put("lapData", createLapData(currentLap));
        }
        return response;
    }

    private Map<String, Object> createLapData(Lap lap) {
        Map<String, Object> lapData = new HashMap<>();
        lapData.put("lapNumber", lap.getLapNumber());
        lapData.put("lapTimeMillis", lap.getLapTimeMillis());
        lapData.put("avgSpeed", calculateAverageSpeed(lap));
        return lapData;
    }

    private Lap checkLapCompletion(LocationData location) {
        Long sessionId = location.getSession().getId();
        LocationData lastLocation = lastLocations.get(sessionId);

        if (lastLocation != null &&
                !isNearStartLine(lastLocation) &&
                isNearStartLine(location)) {
            return createNewLap(location.getSession());
        }
        return null;
    }

    private boolean isNearStartLine(LocationData location) {
        Track track = location.getSession().getTrack();
        double startLineLatitude = track.getLatitude();
        double startLineLongitude = track.getLongitude();

        // Calculate distance using Haversine formula
        double distance = calculateDistance(
                location.getLatitude(),
                location.getLongitude(),
                startLineLatitude,
                startLineLongitude
        );

        return distance <= START_LINE_RADIUS;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Earth's radius in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c * 1000; // Convert to meters
    }

    private Lap createNewLap(Session session) {
        Lap lap = new Lap();
        lap.setSession(session);
        lap.setStartTime(LocalDateTime.now());
        lap.setStatus("IN_PROGRESS");
        lapService.createLap(lap);
        return lap;
    }

    private double calculateAverageSpeed(Lap lap) {
        List<LocationData> lapLocations = locationDataService
                .getLocationsBetweenTimestamps(lap.getStartTime(), lap.getEndTime());
        return lapLocations.stream()
                .mapToDouble(LocationData::getSpeed)
                .average()
                .orElse(0.0);
    }
}
