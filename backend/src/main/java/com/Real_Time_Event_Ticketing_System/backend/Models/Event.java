package com.Real_Time_Event_Ticketing_System.backend.Models;

import java.time.LocalDateTime;

public class Event {

    private static String eventName;
    private static LocalDateTime LocalDateTime;
    private static String location;
    private static Double price;

    public Event(String eventName, java.time.LocalDateTime localDateTime, String location, Double price) {
        this.eventName = eventName;
        LocalDateTime = localDateTime;
        this.location = location;
        this.price = price;
    }

    public static String getEventName() {
        return eventName;
    }

    public static void setEventName(String eventName) {
        Event.eventName = eventName;
    }

    public static java.time.LocalDateTime getLocalDateTime() {
        return LocalDateTime;
    }

    public static void setLocalDateTime(java.time.LocalDateTime localDateTime) {
        LocalDateTime = localDateTime;
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        Event.location = location;
    }

    public static Double getPrice() {
        return price;
    }

    public static void setPrice(Double price) {
        Event.price = price;
    }
}
