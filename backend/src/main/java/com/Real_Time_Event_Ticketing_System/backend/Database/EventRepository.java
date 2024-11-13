package com.Real_Time_Event_Ticketing_System.backend.Database;

import com.Real_Time_Event_Ticketing_System.backend.Models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
