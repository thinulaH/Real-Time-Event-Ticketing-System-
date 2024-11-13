package com.Real_Time_Event_Ticketing_System.backend.Database;
import com.Real_Time_Event_Ticketing_System.backend.Models.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Long> {
}
