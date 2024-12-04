package com.Real_Time_Event_Ticketing_System.backend.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventID;

    private String eventName;
    private LocalDateTime LocalDateTime;
    private String location;
    private Double price;
    private int maxTicketsCount;
    private int availableTicketsCount;

    public Event(String eventName, java.time.LocalDateTime localDateTime, String location, Double price, int maxTicketsCount) {
        this.availableTicketsCount = maxTicketsCount;
        this.eventName = eventName;
        LocalDateTime = localDateTime;
        this.location = location;
        this.maxTicketsCount= maxTicketsCount;
        this.price = price;
    }
    public Event() {}

    public int getMaxTicketsCount() {
        return maxTicketsCount;
    }

    public void setMaxTicketsCount(int maxTicketsCount) {
        this.maxTicketsCount = maxTicketsCount;
    }

    public int getAvailableTicketsCount() {
        return availableTicketsCount;
    }

    public void setAvailableTicketsCount(int availableTicketsCount) {
        this.availableTicketsCount = availableTicketsCount;
    }

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;

    }

    public java.time.LocalDateTime getLocalDateTime() {
        return LocalDateTime;
    }

    public void setLocalDateTime(java.time.LocalDateTime localDateTime) {
        LocalDateTime = localDateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
