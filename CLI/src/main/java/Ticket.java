public class Ticket {
    private int id;
    private String eventName;
    private int ticketPrice;

    public Ticket(int id, String eventName, int ticketPrice) {
        this.id = id;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
