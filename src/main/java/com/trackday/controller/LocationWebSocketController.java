package com.trackday.controller;

import com.trackday.model.LocationData;
import com.trackday.service.LocationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class LocationWebSocketController {
    private final LocationDataService locationDataService;

    @Autowired
    public LocationWebSocketController(LocationDataService locationDataService) {
        this.locationDataService = locationDataService;
    }

    @MessageMapping("/location")
    @SendTo("/topic/location")
    public LocationData sendLocation(LocationData locationData) {
        return locationDataService.recordLocation(locationData);
    }
}
