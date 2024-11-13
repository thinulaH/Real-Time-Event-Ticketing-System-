package com.Real_Time_Event_Ticketing_System.backend.Services;

import com.Real_Time_Event_Ticketing_System.backend.Database.EventRepository;
import com.Real_Time_Event_Ticketing_System.backend.Models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {


    @Autowired
    private EventRepository eventRepository;

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }
}
