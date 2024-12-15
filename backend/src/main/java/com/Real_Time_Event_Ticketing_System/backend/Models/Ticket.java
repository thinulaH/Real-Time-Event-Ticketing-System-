package com.Real_Time_Event_Ticketing_System.backend.Models;

public class Ticket {
        private int id;
        private String eventName;
        private double ticketPrice;
        public static Event event ;

        public Ticket(int id) {
            this.id = id;
            this.eventName = event.getEventName() ;
            this.ticketPrice = event.getPrice();
        }
        public Ticket() {

        }

    public static void setEvent(Event event) {
        Ticket.event = event;
    }

    public int getID() {
            return id;
        }

    public void setID(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return "Ticket [id=" + id + ", eventName=" + eventName + ", ticketPrice=" + ticketPrice + "]";
    }

}
