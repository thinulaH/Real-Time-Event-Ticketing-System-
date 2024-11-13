package com.Real_Time_Event_Ticketing_System.backend.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class CustomerDetails {
    private @Id @GeneratedValue Long id;
    private String name;
    private String email;
    private String phoneNo;
    private int eventNo;
    public int ticketCount;

    public CustomerDetails() {}

    public CustomerDetails(String name, String email, String phoneNo, int eventNo, int ticketCount) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.eventNo = eventNo;
        this.ticketCount = ticketCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public int getEventNo() {
        return eventNo;
    }

    public void setEventNo(int eventNo) {
        this.eventNo = eventNo;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }
}
