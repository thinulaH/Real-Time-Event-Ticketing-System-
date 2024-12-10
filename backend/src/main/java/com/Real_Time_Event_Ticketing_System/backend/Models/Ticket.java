package com.Real_Time_Event_Ticketing_System.backend.Models;

public class Ticket {
        private int id;
        private String eventName;
        private int ticketPrice;

        public Ticket(int id, String eventName, int ticketPrice) {
            this.id = id;
            this.eventName = eventName;
            this.ticketPrice = ticketPrice;
        }
        public Ticket() {

        }

    public Ticket(int ticketID) {
            this.id = ticketID;
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

        public int getTicketPrice() {
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
