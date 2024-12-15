package com.Real_Time_Event_Ticketing_System.backend.Controllers;

import com.Real_Time_Event_Ticketing_System.backend.Database.CustomerDetailsRepository;
import com.Real_Time_Event_Ticketing_System.backend.Models.CustomerDetails;
import com.Real_Time_Event_Ticketing_System.backend.Services.TicketPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class CustomerDetailsController {
    private static final Logger log = LoggerFactory.getLogger(CustomerDetailsController.class);
    private final CustomerDetailsRepository customerDetailsRepository;
    private static TicketPool ticketPool;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger("");

    @Autowired
    public CustomerDetailsController(CustomerDetailsRepository customerDetailsRepository, TicketPool ticketPool) {
        this.customerDetailsRepository = customerDetailsRepository;
        this.ticketPool = ticketPool;
    }

    @PostMapping("/add-Ticket")
    public String saveTicket(@RequestHeader HttpHeaders header) throws InterruptedException {
        final String name = header.getFirst("Name") != null ? header.getFirst("Name") : "Unknown";
        final String email = header.getFirst("Email") != null ? header.getFirst("Email") : "Unknown";
        final String phoneNo = header.getFirst("Phone-Number") != null ? header.getFirst("Phone-Number") : "Unknown";
        logger.info("Received request to save ticket for: " + name);

        Integer ticketID = ticketPool.getTicket();

        if (ticketID == null) {
            logger.warning("No tickets available for: " + name);
            return "No tickets available. Vendor has finished.";
        }
        String time = LocalTime.now().format(DateTimeFormatter.ISO_TIME);
        CustomerDetails customerDetail = new CustomerDetails(ticketID, name, email, phoneNo,time);
        customerDetailsRepository.save(customerDetail);
        return "Ticket saved for " + name;
    }


    @GetMapping("/testCustomer")
    public String test() {
        return "API works correctly";
    }

    @PostMapping("/testFE")
    public String test(@RequestHeader HttpHeaders header) throws InterruptedException {

        final String name = header.getFirst("Name") != null ? header.getFirst("Name") : "Unknown";
        logger.info("Test Front end success" + name);
        return "Test Return" + name;
    }
    @GetMapping("/get-Ticket")
    public List<CustomerDetails> getCustomerDetails() {
        return customerDetailsRepository.findAll();
    }

}






