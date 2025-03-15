package com.culture.association_backend.controller;

import com.culture.association_backend.model.Event;
import com.culture.association_backend.repository.EventRepository;
import com.culture.association_backend.service.EventService;
import com.culture.association_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend to access backend
public class EventController {
    @Autowired
    private  EventRepository eventRepository;

    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventRepository.save(event);
    }
}