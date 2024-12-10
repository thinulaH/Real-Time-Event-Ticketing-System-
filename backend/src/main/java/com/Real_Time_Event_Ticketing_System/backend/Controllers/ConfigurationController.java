package com.Real_Time_Event_Ticketing_System.backend.Controllers;
import com.Real_Time_Event_Ticketing_System.backend.Models.SystemConfiguration;
import com.Real_Time_Event_Ticketing_System.backend.Services.EventConfigurationManager;
import com.Real_Time_Event_Ticketing_System.backend.Services.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
public class ConfigurationController {

    private TicketPool ticketPool;
    private final SystemConfiguration systemConfiguration;
    private final EventConfigurationManager eventConfigurationManager;

    @Autowired
    public ConfigurationController(TicketPool ticketPool, SystemConfiguration systemConfiguration, EventConfigurationManager eventConfigurationManager) {
        this.ticketPool = ticketPool;
        this.systemConfiguration = systemConfiguration;
        this.eventConfigurationManager = eventConfigurationManager;
    }

    @PostMapping("/set-config")
    public String updateConfiguration(@RequestBody SystemConfiguration newConfig) {
        // Update system configuration
        systemConfiguration.setTotalTickets(newConfig.getTotalTickets());
        systemConfiguration.setTicketReleaseRate(newConfig.getTicketReleaseRate());
        systemConfiguration.setCustomerRetrievalRate(newConfig.getCustomerRetrievalRate());
        systemConfiguration.setMaxTicketCapacity(newConfig.getMaxTicketCapacity());

        // Update the TicketPool instance
        ticketPool.setTotalTickets(systemConfiguration.getTotalTickets());
        ticketPool.setMaximumCapacity(systemConfiguration.getMaxTicketCapacity());

        return "Configuration updated successfully!";
    }

    // Endpoint to retrieve current configuration parameters
    @GetMapping("/get-config")
    public SystemConfiguration getConfiguration() {
        return systemConfiguration;
    }

    // Endpoint to start the system
    @PostMapping("/start")
    public String startSystem() {
        return eventConfigurationManager.startSystem();
    }

    // Endpoint to stop the system
    @PostMapping("/stop")
    public String stopSystem() {
        return eventConfigurationManager.stopSystem();
    }
}