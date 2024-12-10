package com.Real_Time_Event_Ticketing_System.backend;

import com.Real_Time_Event_Ticketing_System.backend.Database.CustomerDetailsRepository;
import com.Real_Time_Event_Ticketing_System.backend.Models.CustomerDetails;
import com.Real_Time_Event_Ticketing_System.backend.Models.Ticket;
import com.Real_Time_Event_Ticketing_System.backend.Services.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;
import static com.Real_Time_Event_Ticketing_System.backend.Services.EventConfigurationManager.isRunning;


public class Customer implements Runnable {
    private static final Logger logger = Logger.getLogger("");
    private final TicketPool ticketPool;
    private CustomerDetailsRepository customerDetailsRepository;


    public Customer(TicketPool ticketPool, CustomerDetailsRepository customerDetailsRepository) {
        this.ticketPool = ticketPool;
        this.customerDetailsRepository = customerDetailsRepository;
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                if (ticketPool.getTicketQueue().isEmpty() && ticketPool.isVendorFinished()) {
                    logger.info("No more tickets to remove. Customer stopping.");
                    break;
                }
                logger.info("Customer removing ticket...");
                Ticket ticket = ticketPool.getTicketQueue().peek();
                ticketPool.removeTicket();

                if (customerDetailsRepository != null) {
                    CustomerDetails customerDetail = createcustomerDetails(ticket);
                    customerDetailsRepository.save(customerDetail);
                } else {
                    logger.warning("customerDetailsRepository is null. Cannot save CustomerDetails.");
                }

                System.out.println("Ticket removed: " + ticket);
                Thread.sleep(ticketPool.getRetrievalRate() * 1000); // Convert to milliseconds
            }
        } catch (InterruptedException e) {
            logger.info("Customer interrupted, exiting...");
            Thread.currentThread().interrupt();
        }
    }
    private CustomerDetails createcustomerDetails(Ticket ticket){
        String name = "Customer"+ticket.getID();
        String email = "customer"+ticket.getID()+".email@example.com";
        String phoneNo = "1234567890";
        return new CustomerDetails(ticket.getID(), name,email, phoneNo);
    }
}
