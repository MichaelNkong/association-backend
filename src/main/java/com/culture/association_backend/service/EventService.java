package com.culture.association_backend.service;

import com.culture.association_backend.model.Event;
import com.culture.association_backend.model.Member;
import com.culture.association_backend.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllMembers() {
        return eventRepository.findAll();
    }
}