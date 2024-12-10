package com.Real_Time_Event_Ticketing_System.backend.Services;

import com.Real_Time_Event_Ticketing_System.backend.Database.CustomerDetailsRepository;
import com.Real_Time_Event_Ticketing_System.backend.Models.CustomerDetails;
import com.Real_Time_Event_Ticketing_System.backend.Models.Ticket;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

@Service
public class TicketPool {
    private static final Logger logger = Logger.getLogger("");
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final Queue<Ticket> ticketQueue = new LinkedList<>();
    private CustomerDetailsRepository customerDetailsRepository;
    private int maximumCapacity;
    private int totalTickets;
    private int releaseRate;
    private int retrievalRate;
    private boolean vendorFinished = false; // Flag to track if vendor finished

    public TicketPool() {
        // Default initialization
        this.totalTickets = 100;
        this.maximumCapacity = 50;
    }

    public TicketPool(int totalTickets, int maximumCapacity) {
        this.totalTickets = totalTickets;
        this.maximumCapacity = maximumCapacity;
    }

    public synchronized void addTicket(Ticket ticket) throws InterruptedException {

        lock.lock();
        try {
            while (ticketQueue.size() >= maximumCapacity) {
                logger.info("Queue is full. Vendor waiting...");
                notFull.await();
            }
            if (totalTickets > 0) {
                int ticketID = totalTickets--;
                ticket.setID(ticketID);
                ticketQueue.add(ticket);
                logger.info("Added Ticket{id=" + ticketID + "}. Available ticket count: " + ticketQueue.size());
                notEmpty.signalAll();
            } else {
                logger.info("No more tickets to add.");
                vendorFinished = true;
            }
        } finally {
            lock.unlock();
        }
    }

    public synchronized Integer removeTicket() throws InterruptedException {
        lock.lock();
        try {
            while (ticketQueue.isEmpty() && !vendorFinished) {
                logger.info("Queue is empty, customer waiting...");
                notEmpty.await(); // Customer is waiting for a ticket to be available
            }
            if (!ticketQueue.isEmpty()) {
                Ticket ticket = ticketQueue.remove();
                logger.info("Removed ticket: " + ticket.getID());
                notFull.signalAll();
                return ticket.getID();
            }
        } finally {
            lock.unlock();
        }
        return null;  // Return null if no ticket was removed
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
    }

    public Integer getTicket() throws InterruptedException {
        lock.lock();
        try {
//            while (ticketQueue.isEmpty() && !vendorFinished) {
//                logger.info("Queue is empty, customer waiting...");
//                notEmpty.await(); // Use the condition to wait
//            }

            if (!ticketQueue.isEmpty()) {
                Ticket ticket = ticketQueue.remove();
                logger.info("Removed ticket: " + ticket.getID());
                notFull.signalAll();
                return ticket.getID();
            }

            return null;
        } finally {
            lock.unlock();
        }
    }
}
