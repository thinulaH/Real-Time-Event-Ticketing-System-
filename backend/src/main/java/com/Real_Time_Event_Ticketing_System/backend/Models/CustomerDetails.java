package com.Real_Time_Event_Ticketing_System.backend.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_details")
public class CustomerDetails {
    @Id
    private Integer id;  // Change from int to Integer

    @Column(name = "ticket_id")
    private Integer ticketId;  // Explicitly map ticket ID

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_no")
    private String phoneNo;


    public CustomerDetails() {}
    public CustomerDetails(Integer ticketId, String name, String email, String phoneNo) {
        this.id = ticketId;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
    }



    public int getId() {
        return id;
    }

    public void setId(Integer id) {
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


}
