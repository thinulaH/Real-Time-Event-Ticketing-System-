package com.Real_Time_Event_Ticketing_System.backend.Controllers;


import com.Real_Time_Event_Ticketing_System.backend.Database.CustomerDetailsRepository;
import com.Real_Time_Event_Ticketing_System.backend.Models.CustomerDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@RestController
public class CustomerDetailsController {
    private CustomerDetailsRepository customerDetailsRepository;

    public CustomerDetailsController(CustomerDetailsRepository customerDetailsRepository) {
        this.customerDetailsRepository = customerDetailsRepository;
    }
    @GetMapping("/testCustomer")
    public String test(){
        return "API works correctly";
    }
    @PostMapping("/add-Ticket")
    public String saveTicket(@RequestHeader HttpHeaders header){
        final String name = String.valueOf(header.getFirst("Name"));
        final String email = String.valueOf(header.getFirst("Email"));
        final String phoneNo = String.valueOf(header.getFirst("Phone-Number"));
        final int ticketCount = Integer.parseInt(String.valueOf(header.getFirst("Ticket-Count")));
        final int eventNo = Integer.parseInt(String.valueOf(header.getFirst("Event-No")));
        CustomerDetails customerDetails = new CustomerDetails(name, email, phoneNo, eventNo, ticketCount);
        customerDetailsRepository.save(customerDetails);
        return "Saved ticket successfully: "+customerDetails;
    }
    @GetMapping("/get-Ticket")
    public List<CustomerDetails> getCustomerDetails(){
        return customerDetailsRepository.findAll();
    }




}
