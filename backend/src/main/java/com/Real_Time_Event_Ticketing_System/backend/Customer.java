package com.Real_Time_Event_Ticketing_System.backend;

import com.Real_Time_Event_Ticketing_System.backend.Services.TicketPool;

import java.util.logging.Logger;
import static com.Real_Time_Event_Ticketing_System.backend.CLI.CLI.isRunning;

public class Customer implements Runnable {
    private static final Logger logger = Logger.getLogger("");
    private final TicketPool ticketPool;

    public Customer(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                if (ticketPool.getTicketQueue().isEmpty() && ticketPool.isVendorFinished()) {
                    logger.info("No more tickets to remove. Customer stopping.");
                    break;  // Exit if the vendor is finished and the queue is empty
                }

                logger.info("Customer removing ticket...");
                ticketPool.buyTicket();
                // Sleep and check for interruptions.
                Thread.sleep(ticketPool.getRetrievalRate()*1000);  // Convert to milliseconds
            }
        } catch (InterruptedException e) {
            logger.info("Customer interrupted, exiting...");
            Thread.currentThread().interrupt(); // Handle interruption gracefully
        }
    }
}

