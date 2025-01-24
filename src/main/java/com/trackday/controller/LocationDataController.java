package com.trackday.controller;

import com.trackday.model.LocationData;
import com.trackday.service.LocationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationDataController {
    private final LocationDataService locationDataService;

    @Autowired
    public LocationDataController(LocationDataService locationDataService) {
        this.locationDataService = locationDataService;
    }

    @PostMapping
    public ResponseEntity<LocationData> recordLocation(@RequestBody LocationData locationData) {
        return ResponseEntity.ok(locationDataService.recordLocation(locationData));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationData> getLocation(@PathVariable Long id) {
        return ResponseEntity.ok(locationDataService.getLocationById(id));
    }

    @GetMapping
    public ResponseEntity<List<LocationData>> getAllLocations() {
        return ResponseEntity.ok(locationDataService.getAllLocations());
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<LocationData>> getSessionLocations(@PathVariable Long sessionId) {
        return ResponseEntity.ok(locationDataService.getSessionLocations(sessionId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationData> updateLocation(@PathVariable Long id, @RequestBody LocationData locationData) {
        return ResponseEntity.ok(locationDataService.updateLocation(id, locationData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationDataService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
