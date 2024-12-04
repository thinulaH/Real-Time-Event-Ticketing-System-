package com.Real_Time_Event_Ticketing_System.backend.Services;

import com.Real_Time_Event_Ticketing_System.backend.Database.EventRepository;
import com.Real_Time_Event_Ticketing_System.backend.Models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class EventService {


    @Autowired
    private EventRepository eventRepository;

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    @Transactional
    public void updateEvent(Long eventID, String fieldToUpdate, String newValue) {
        if (eventRepository.existsById(eventID)) {
            Event event = eventRepository.findById(eventID).orElse(null);
            if (event != null) {
                switch (fieldToUpdate) {
                    case "name":
                        event.setEventName(newValue);
                        break;
                    case "location":
                        event.setLocation(newValue);
                        break;
                    case "capacity":
                        try {
                            int capacity = Integer.parseInt(newValue);
                            event.setMaxTicketsCount(capacity);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid capacity value. Please enter a number.");
                        }
                        break;
                    case "date":
                        String eventDateStr = newValue;

                        try {
                            event.setLocalDateTime(LocalDateTime.parse(eventDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use 'yyyy-MM-dd HH:mm'.");
                        }

                        break;
                    case "ticketPrice":
                        try {
                            double price = Double.parseDouble(newValue);
                            event.setPrice(price);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid ticket price. Please enter a number.");
                        }
                        break;
                    default:
                        System.out.println("Invalid field to update.");
                        return;
                }
                eventRepository.save(event);
                System.out.println("Event updated successfully.");
            }
        } else {
            System.out.println("Event ID not found.");
        }
    }

}
