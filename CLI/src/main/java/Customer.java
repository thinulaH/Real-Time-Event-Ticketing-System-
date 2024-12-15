import java.util.logging.Logger;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private String customerName;
    private static final Logger logger = LoggerConfig.getLogger(Customer.class.getName());
    public Customer(TicketPool ticketPool, String customerName) {
        this.ticketPool = ticketPool;
        this.customerName = customerName;
    }

    @Override
    public void run() {
        try {
            while (MainCLI.isRunning) {
                if (ticketPool.getTicketQueue().isEmpty() && ticketPool.isVendorFinished()) {
                    logger.info("No more tickets to remove. Customer stopping.");
                    break;
                }
                logger.info(customerName +" removing ticket...");
                ticketPool.buyTicket();
                Thread.sleep(ticketPool.getRetrievalRate() * 1000); // Convert to milliseconds
            }
        } catch (InterruptedException e) {
            logger.info("Customer interrupted, exiting...");
            Thread.currentThread().interrupt();
        }
    }
}