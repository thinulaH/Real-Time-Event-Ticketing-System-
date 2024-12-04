package com.Real_Time_Event_Ticketing_System.backend.Services;

import com.Real_Time_Event_Ticketing_System.backend.Database.CustomerDetailsRepository;
import com.Real_Time_Event_Ticketing_System.backend.Models.CustomerDetails;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class TicketPool {
    private static final Logger logger = Logger.getLogger("");
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final Queue<Integer> ticketQueue = new LinkedList<>();

    private CustomerDetailsRepository customerDetailsRepository;
    private final int maxCapacity;
    private int totalTickets;
    private int releaseRate;
    private int retrievalRate;
    private int eventID;
    private boolean vendorFinished = false;  // Flag to track if vendor finished

    public TicketPool(int totalTickets, int maxCapacity) {
        this.totalTickets = totalTickets;
        this.maxCapacity = maxCapacity;
    }

    public void setCustomerDetailsRepository(CustomerDetailsRepository repository) {
        this.customerDetailsRepository = repository;
    }

    public void addTicket() throws InterruptedException {
        lock.lock();
        try {
            if (totalTickets > 0) { // Only add tickets if there are still tickets to add
                int ticketID = totalTickets--;
                ticketQueue.add(ticketID);
                logger.info("Added Ticket " + ticketID+ " available ticket count : " + ticketQueue.size());
                notEmpty.signalAll();
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
                int ticketID = ticketQueue.remove();
                logger.info("Customer completed a ticket purchase " + ticketID+ " available ticket count : " + ticketQueue.size());

                // Ensure customerDetailsRepository is not null before attempting to save
                if (customerDetailsRepository != null) {
                    CustomerDetails customerDetail = new CustomerDetails("Test"+ticketID, "test"+ticketID+"@test.com", "0123456789", eventID, 1);
                    customerDetailsRepository.save(customerDetail);
                } else {
                    logger.warning("customerDetailsRepository is null. Cannot save CustomerDetails.");
                }

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

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public Queue<Integer> getTicketQueue() {
        return ticketQueue;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setVendorFinished(boolean vendorFinished) {
        this.vendorFinished = vendorFinished;
    }
}