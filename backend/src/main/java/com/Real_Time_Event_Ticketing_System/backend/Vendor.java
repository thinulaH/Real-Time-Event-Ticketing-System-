package com.Real_Time_Event_Ticketing_System.backend;

import com.Real_Time_Event_Ticketing_System.backend.Config.LoggerConfig;
import com.Real_Time_Event_Ticketing_System.backend.Models.Ticket;
import com.Real_Time_Event_Ticketing_System.backend.Services.TicketPool;

import java.util.logging.Logger;

import static com.Real_Time_Event_Ticketing_System.backend.Services.EventConfigurationManager.isRunning;

public class Vendor implements Runnable {
    private static TicketPool ticketPool;
    private static final Logger logger = LoggerConfig.getLogger(Vendor.class.getName());

    public Vendor(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        String vendorName = "Vendor";
        try {
            while (isRunning && !ticketPool.isVendorFinished()) {
                // Check if total tickets are exhausted
                if (ticketPool.getTotalTickets() <= 0) {
                    ticketPool.setVendorFinished(true);
                    logger.info(vendorName + " finished adding tickets.");
                    break;
                }

                // Create and add ticket
                logger.info(vendorName + " adding ticket...");
                int price = 2000;
                Ticket ticketAdd = new Ticket(ticketPool.getTotalTickets() - 1);

                // Add ticket with proper error handling
                try {
                    ticketPool.addTicket(ticketAdd);
                } catch (InterruptedException e) {
                    logger.warning(vendorName + " interrupted while adding ticket");
                    Thread.currentThread().interrupt();
                    break;
                }

                // Sleep between ticket additions
                Thread.sleep(ticketPool.getReleaseRate() * 1000);
            }
        } catch (Exception e) {
            logger.severe(vendorName + " encountered an unexpected error: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
