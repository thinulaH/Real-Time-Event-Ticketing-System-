public class Ticket {
    private int id;
    private String vendorName;
    private int ticketPrice;
    private String customerName;

    public Ticket(int id, String vendorName, int ticketPrice, String customerName) {
        this.id = id;
        this.vendorName = vendorName;
        this.ticketPrice = ticketPrice;
        this.customerName = customerName;
    }

    public Ticket(int id, String vendorName, int ticketPrice) {
        this.id = id;
        this.vendorName = vendorName;
        this.ticketPrice = ticketPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return vendorName;
    }

    public void setEventName(String eventName) {
        this.vendorName = eventName;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "Ticket [id=" + getId() + ", vendorName=" + vendorName + ", ticketPrice=" + ticketPrice + "]";
    }
}
