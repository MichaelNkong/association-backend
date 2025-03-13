package com.culture.association_backend;


import com.culture.association_backend.model.Event;
import com.culture.association_backend.model.Role;
import com.culture.association_backend.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private EventRepository eventRepository;



    @Override
    public void run(String... args) throws Exception {
        Event event1 = new Event();
        event1.setName("Music Festival");
        event1.setLocation("Central Park");
        event1.setDate(LocalDate.parse("2025-05-12"));
        event1.setEstimatedPrice(10.5);
        event1.setDescription("A great music event");

        Event event2 = new Event();
        event2.setName("Tech Conference");
        event2.setLocation("Los Angeles");
        event2.setDate(LocalDate.parse("2025-05-25"));
        event2.setEstimatedPrice(200.5);
        event2.setDescription("The latest in tech");



       // eventRepository.save(event1);
       // eventRepository.save(event2);

    }
}