package com.Real_Time_Event_Ticketing_System.backend.Services;

import com.Real_Time_Event_Ticketing_System.backend.Models.SystemConfiguration;
import com.Real_Time_Event_Ticketing_System.backend.Customer;
import com.Real_Time_Event_Ticketing_System.backend.Database.CustomerDetailsRepository;
import com.Real_Time_Event_Ticketing_System.backend.Vendor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

@Service
public class EventConfigurationManager {

    private final SystemConfiguration configuration;
    private TicketPool ticketPool;
    private boolean systemStarted;
    private final Lock configLock;
    public static boolean isRunning = false;
    private final CustomerDetailsRepository customerDetailsRepository;
    private ExecutorService executorService; // Executor service to manage threads
    private static final Logger logger = Logger.getLogger(EventConfigurationManager.class.getName());

    // Constructor with dependency injection for CustomerDetailsRepository
    public EventConfigurationManager(SystemConfiguration configuration,
                                     CustomerDetailsRepository customerDetailsRepository) {
        this.configuration = configuration;
        this.customerDetailsRepository = customerDetailsRepository;
        this.systemStarted = false;
        isRunning = false;
        this.configLock = new ReentrantLock(); // Initialize the lock
    }

    // Method to start the system logic
    public String startSystem() {
        if (configuration.getTotalTickets() == 0) {
            return "Error: Configuration parameters are missing. Please set them first!";
        }
        if (systemStarted) {
            return "System is already running.";
        }

        // Lock the configuration for thread-safety
        configLock.lock();
        try {
            ticketPool = new TicketPool(configuration.getTotalTickets(), configuration.getMaxTicketCapacity());
            ticketPool.setReleaseRate(configuration.getTicketReleaseRate());
            ticketPool.setRetrievalRate(configuration.getCustomerRetrievalRate());

            systemStarted = true;  // Mark the system as started
            isRunning = true; // Mark the system as running
            startTicketingOperation(); // Start the ticketing operations
            logger.info("System started successfully!");
            return "System started successfully!";
        } finally {
            configLock.unlock();  // Always unlock after the configuration is done
        }
    }

    public String stopSystem() {
        if (!isRunning) {
            return "System is not running.";
        }

        // Stop the ticketing operation and reset states
        stopTicketingOperation();
        isRunning = false;
        systemStarted = false;
        ticketPool = null; // Reset the ticket pool or handle as needed
        logger.info("System stopped successfully.");
        return "System stopped successfully.";
    }

    // Method to start ticketing operations with ExecutorService
    private void startTicketingOperation() {
        System.out.println("Starting ticket operations...");
        isRunning = true;
        executorService = Executors.newFixedThreadPool(2); // Create a thread pool for Vendor and Customer operations
        System.out.println(ticketPool.toString());
        executorService.execute(new Vendor(ticketPool));  // Start the vendor thread
        executorService.execute(new Customer(ticketPool,customerDetailsRepository));  // Start the customer thread
    }

    // Method to stop ticketing operations by shutting down ExecutorService
    private void stopTicketingOperation() {
        if (isRunning) {
            System.out.println("Stopping ticket operations...");
            isRunning = false;  // Signal to stop
            executorService.shutdownNow(); // Immediately shutdown the executor service
            logger.info("Stopping vendor and customer threads...");
        }
    }

    // Method to handle interactive configuration options
    public void runConfiguration() {
        // You can implement the CLI logic here for starting, stopping, and exiting operations
    }

    // Additional getters for system status
    public boolean isSystemStarted() {
        return systemStarted;
    }

    // Getter for ticketPool
    public TicketPool getTicketPool() {
        return ticketPool;
    }
}