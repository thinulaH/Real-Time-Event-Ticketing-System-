package com.Real_Time_Event_Ticketing_System.backend.Controllers;


import com.Real_Time_Event_Ticketing_System.backend.Database.CustomerDetailsRepository;
import com.Real_Time_Event_Ticketing_System.backend.Models.CustomerDetails;
import com.Real_Time_Event_Ticketing_System.backend.Models.Ticket;
import com.Real_Time_Event_Ticketing_System.backend.Services.TicketPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Optional;

import static com.Real_Time_Event_Ticketing_System.backend.Services.EventConfigurationManager.isRunning;


@RestController
public class CustomerDetailsController {
    private static final Logger log = LoggerFactory.getLogger(CustomerDetailsController.class);
    private final CustomerDetailsRepository customerDetailsRepository;
    private final TicketPool ticketPool;
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
        Integer ticketID = ticketPool.getTicket(); // Removes the ticket
        System.out.println(ticketID);
        CustomerDetails customerDetail = new CustomerDetails(1, name, email, phoneNo);
        customerDetailsRepository.save(customerDetail);
        return "Ticket saved for " + name;
    }


    @GetMapping("/testCustomer")
    public String test() {
        return "API works correctly";
    }
    @GetMapping("/get-Ticket")
    public List<CustomerDetails> getCustomerDetails() {
        return customerDetailsRepository.findAll();
    }
}






