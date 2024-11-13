package com.Real_Time_Event_Ticketing_System.backend;

import com.Real_Time_Event_Ticketing_System.backend.Services.TicketPool;

import java.util.logging.Logger;
import static com.Real_Time_Event_Ticketing_System.backend.CLI.CLI.isRunning;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private static final Logger logger = Logger.getLogger("");

    public Vendor(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        try {
            while (isRunning && (ticketPool.getTotalTickets() >= ticketPool.getMaxCapacity())) {
                logger.info("Vendor adding ticket...");
                ticketPool.addTicket();  // Add ticket to the pool
                Thread.sleep(ticketPool.getReleaseRate()*1000);  // Sleep in seconds (converted to ms)
            }
            logger.info("Vendor finished adding tickets.");
            ticketPool.setVendorFinished(true);
        } catch (InterruptedException e) {
            logger.info("Vendor interrupted, exiting...");
            Thread.currentThread().interrupt();
        }
    }
}
