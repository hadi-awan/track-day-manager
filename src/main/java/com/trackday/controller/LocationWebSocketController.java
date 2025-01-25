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

@Controller
public class LocationWebSocketController {
    private final LocationDataService locationDataService;
    private final LapService lapService;

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
    public LocationData trackLocation(LocationData locationData) {
        LocationData saved = locationDataService.recordLocation(locationData);
        checkLapCompletion(saved);
        return saved;
    }

    private void checkLapCompletion(LocationData location) {
        if (isNearStartLine(location)) {
            // Create new lap
            createNewLap(location.getSession());
        }
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

    private void createNewLap(Session session) {
        Lap lap = new Lap();
        lap.setSession(session);
        lap.setStartTime(LocalDateTime.now());
        lap.setStatus("IN_PROGRESS");
        lapService.createLap(lap);
    }
}
