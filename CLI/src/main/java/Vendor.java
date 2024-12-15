import java.util.logging.Logger;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private String vendorName;
    private static final Logger logger = LoggerConfig.getLogger(Vendor.class.getName());

    public Vendor(TicketPool ticketPool, String vendorName) {
        this.ticketPool = ticketPool;
        this.vendorName = vendorName;
    }

    @Override
    public void run() {
        try {
            while (MainCLI.isRunning && !ticketPool.isVendorFinished()) {
                if (ticketPool.getTotalTickets() <= 0) {
                    ticketPool.setVendorFinished(true);
                    break;
                }
                logger.info(vendorName + " adding ticket...");
                int price = 2000;
                Ticket ticketAdd = new Ticket(ticketPool.getTotalTickets() - 1, vendorName, price);
                ticketPool.addTicket(ticketAdd);
                Thread.sleep(ticketPool.getReleaseRate() * 1000);
            }

            logger.info(vendorName + " finished adding tickets.");
        } catch (InterruptedException e) {
            logger.info(vendorName + " interrupted, exiting...");
            Thread.currentThread().interrupt();
        }
    }
}