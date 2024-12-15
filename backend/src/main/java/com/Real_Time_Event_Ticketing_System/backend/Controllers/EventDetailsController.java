package com.Real_Time_Event_Ticketing_System.backend.Controllers;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/event")
public class EventDetailsController {

    @GetMapping("/eventDetails")
    public String getEventDetails() {
        return "{\"eventName\":\"IIT Career Fair 2025\",\"location\":\"BMICH\"}";
    }
}