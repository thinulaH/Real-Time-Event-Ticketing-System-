import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class TicketPool {
    private static final Logger logger = LoggerConfig.getLogger(TicketPool.class.getName());
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final Queue<Ticket> ticketQueue = new LinkedList<>();
    private final int maximumCapacity;
    private int totalTickets;
    private int releaseRate;
    private int retrievalRate;
    private boolean vendorFinished = false; // Flag to track if vendor finished
    private int remainingTickets;

    public TicketPool(int totalTickets, int maximumCapacity) {
        this.totalTickets = totalTickets;
        this.maximumCapacity = maximumCapacity;
        this.remainingTickets = totalTickets;
    }

    public synchronized void addTicket(Ticket ticket) throws InterruptedException {
        lock.lock();
        try {
            while (ticketQueue.size() >= maximumCapacity) { // Wait if the queue is at maximum capacity
                logger.info("Queue is full. Vendor waiting...");
                notFull.await(); // Wait until space is available
            }
            if (remainingTickets > 0) { // Only add tickets if there are still tickets to add
                int ticketID = totalTickets - remainingTickets + 1;
                remainingTickets--;
                ticket.setId(ticketID);
                ticketQueue.add(ticket);
                logger.info("Ticket " + ticketID + " added successfully. Available ticket count: " + ticketQueue.size());
                notEmpty.signalAll(); // Notify customers
            } else {
                logger.info("No more tickets to add.");
                vendorFinished = true; // Vendor finished adding tickets
            }
        } finally {
            lock.unlock();
        }
    }

    public void buyTicket() throws InterruptedException {
        lock.lock();
        try {
            while (ticketQueue.isEmpty() && !vendorFinished) {
                logger.info("Queue is empty, customer waiting...");
                notEmpty.await();
            }
            if (!ticketQueue.isEmpty()) {
                Ticket ticket = ticketQueue.remove();
                logger.info("Ticket " + ticket.getId() + " bought successfully. Remaining ticket count: " + ticketQueue.size());
                notFull.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean isVendorFinished() {
        return vendorFinished;
    }

    public void setReleaseRate(int rate) {
        this.releaseRate = rate;
    }

    public void setRetrievalRate(int rate) {
        this.retrievalRate = rate;
    }

    public int getReleaseRate() {
        return releaseRate;
    }

    public int getRetrievalRate() {
        return retrievalRate;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public Queue<Ticket> getTicketQueue() {
        return ticketQueue;
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setVendorFinished(boolean vendorFinished) {
        this.vendorFinished = vendorFinished;
    }

}