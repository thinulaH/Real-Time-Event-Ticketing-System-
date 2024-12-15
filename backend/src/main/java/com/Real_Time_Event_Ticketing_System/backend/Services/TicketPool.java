package com.Real_Time_Event_Ticketing_System.backend.Services;

import com.Real_Time_Event_Ticketing_System.backend.Database.CustomerDetailsRepository;
import com.Real_Time_Event_Ticketing_System.backend.Models.Ticket;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

@Service
public class TicketPool {
    private static final Logger logger = Logger.getLogger("");
    private static final ReentrantLock lock = new ReentrantLock();
    private static Condition notFull = lock.newCondition();
    private static Condition notEmpty = lock.newCondition();
    private final Condition ticketAvailable = lock.newCondition();

    private static Queue<Ticket> ticketQueue = new LinkedList<>();
    private CustomerDetailsRepository customerDetailsRepository;
    private int maximumCapacity;
    private int totalTickets;
    private int releaseRate;
    private int retrievalRate;
    private boolean vendorFinished = false; // Flag to track if vendor finished
    private int remainingTickets;


    public TicketPool() {
        this.totalTickets = 100;
        this.maximumCapacity = 50;
        this.remainingTickets = totalTickets;
    }

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
                ticket.setID(ticketID);
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

    public Integer buyTicket() throws InterruptedException {
        lock.lock();
        try {
            while (ticketQueue.isEmpty() && !vendorFinished) {
                logger.info("Queue is empty, customer waiting...");
                notEmpty.await();
            }
            if (!ticketQueue.isEmpty()) {
                Ticket ticket = ticketQueue.remove();
                logger.info("Ticket " + ticket.getID() + " bought successfully. Remaining ticket count: " + ticketQueue.size());
                notFull.signalAll();
                return ticket.getID();
            }
        } finally {
            lock.unlock();
        }
        return 0;
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

    public void setMaximumCapacity(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
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

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
        this.remainingTickets = totalTickets;
    }

    public synchronized Integer getTicket() throws InterruptedException {
        lock.lock();
        try {
            while (ticketQueue.isEmpty()) {
                if (vendorFinished) {
                    logger.warning("No tickets available and vendor has finished.");
                    return 3; // Vendor finished, no tickets available
                }
                logger.warning("Queue is empty. Waiting for tickets...");
                notEmpty.await(); // Wait until a ticket is added
            }

            Ticket ticket = ticketQueue.remove();
            logger.info("Ticket retrieved successfully: " + ticket.getID() + ". Remaining tickets: " + ticketQueue.size());
            notFull.signalAll(); // Notify vendor that space is available
            return ticket.getID();
        } finally {
            lock.unlock();
        }
    }
}
