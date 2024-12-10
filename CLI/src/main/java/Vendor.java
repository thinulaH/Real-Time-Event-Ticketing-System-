import java.util.logging.Logger;


public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private static final Logger logger = Logger.getLogger("");

    public Vendor(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        for (int i = 0; i < ticketPool.getTotalTickets(); i++) {
            try {
                while (MainCLI.isRunning && !ticketPool.isVendorFinished()) {
                    logger.info("Vendor adding ticket...");
                    int price = 2000;
                    Ticket ticketadd = new Ticket(i, "TestEvent", price);
                    ticketPool.addTicket(ticketadd); // Add ticket to the pool
                    Thread.sleep(ticketPool.getReleaseRate() * 1000); // Sleep in seconds (converted to ms)
                }
                logger.info("Vendor finished adding tickets.");
            } catch (InterruptedException e) {
                logger.info("Vendor interrupted, exiting...");
                Thread.currentThread().interrupt();
            }
        }
    }
}