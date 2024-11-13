package com.Real_Time_Event_Ticketing_System.backend.CLI;
import com.Real_Time_Event_Ticketing_System.backend.Customer;
import com.Real_Time_Event_Ticketing_System.backend.Database.EventRepository;
import com.Real_Time_Event_Ticketing_System.backend.Models.Event;
import com.Real_Time_Event_Ticketing_System.backend.Services.EventService;
import com.Real_Time_Event_Ticketing_System.backend.Services.TicketPool;
import com.Real_Time_Event_Ticketing_System.backend.Vendor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import java.util.concurrent.locks.ReentrantLock;

@Component
public class CLI implements CommandLineRunner {
    private static final Logger logger = Logger.getLogger("");
    private TicketPool ticketPool;
    public static boolean isRunning = false;
    private static ExecutorService executorService;

    private final EventRepository eventRepository;
    private final EventService eventService;
    private Scanner input = new Scanner(System.in);

    private final ReentrantLock configLock = new ReentrantLock();  // ReentrantLock for configuration

    public CLI(EventService eventService, EventRepository eventRepository) {
        this.eventService = eventService;
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0) {
            String command = args[0];
            logger.info("Received command: " + command);  // Log the command
            switch (command) {
                case "addEvent":
                    addEvent();
                    break;
                case "deleteEvent":
                    deleteEvent();
                    break;
                case "systemConfiguration":
                    systemConfiguration(input);
                    runConfiguration();
                    break;
                default:
                    logger.warning("Invalid command: " + command);
            }
        } else {
            logger.warning("No arguments provided.");
        }
    }

    private void runConfiguration() {
        while (true) {
            System.out.println("Select an option: \n\t1 - Start\n\t2 - Stop\n\t3 - Exit");
            String option = input.next();

            switch (option) {
                case "1":
                    if (!isRunning) {
                        startTicketingOperation();
                    } else {
                        System.out.println("System is already running.");
                    }
                    break;
                case "2":
                    if (isRunning) {
                        stopTicketingOperation();
                    } else {
                        System.out.println("Operations are not running.");
                    }
                    break;
                case "3":
                    stopTicketingOperation();
                    System.out.println("Exiting program...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void systemConfiguration(Scanner scanner) {
        System.out.print("Enter total tickets: ");
        int totalTickets = validInput(scanner);
        System.out.print("Enter ticket release rate (in seconds): ");
        int ticketReleaseRate = validInput(scanner);
        System.out.print("Enter customer retrieval rate (in seconds): ");
        int customerRetrievalRate = validInput(scanner);
        System.out.print("Enter max ticket capacity: ");
        int maxTicketCapacity = validInput(scanner);

        // Locking the configuration block
        configLock.lock();
        try {
            ticketPool = new TicketPool(totalTickets, maxTicketCapacity);  // Initialize ticketPool
            ticketPool.setReleaseRate(ticketReleaseRate);
            ticketPool.setRetrievalRate(customerRetrievalRate);
        } finally {
            configLock.unlock();  // Unlock once configuration is done
        }
    }

    private static int validInput(Scanner scanner) {
        int input;
        while (true) {
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input > 0) {
                    break;
                } else {
                    System.out.println("Please enter a positive integer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a positive integer.");
            }
        }
        return input;
    }

    private void startTicketingOperation() {
        System.out.println("Starting ticket Operations...");
        isRunning = true;
        executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Vendor(ticketPool));
        executorService.execute(new Customer(ticketPool));
    }

    private void stopTicketingOperation() {
        if (isRunning) {
            System.out.println("Stopping ticket Operations...");
            isRunning = false;  // Signal to stop
            executorService.shutdownNow(); // Immediately shutdown the executor service
            logger.info("Stopping vendor and customer threads...");
        }
    }

    private void deleteEvent() {
        System.out.print("Enter event ID to delete: ");
        Long eventID = input.nextLong();
        if (eventRepository.existsById(eventID)) {
            eventRepository.deleteById(eventID);
            System.out.println("Event with ID " + eventID + " has been deleted.");
        } else {
            System.out.println("No event found with ID " + eventID);
        }
    }

    private void addEvent() {
        System.out.print("Enter event name: ");
        String eventName = input.nextLine();

        System.out.print("Enter event date (yyyy-MM-dd HH:mm)");
        String eventDateStr = input.nextLine();
        LocalDateTime eventDate = LocalDateTime.parse(eventDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        System.out.print("Enter event location:");
        String eventLocation = input.nextLine();

        System.out.print("Enter ticket price: ");
        double ticketPrice = input.nextDouble();

        Event event = new Event(eventName, eventDate, eventLocation, ticketPrice);
        eventService.saveEvent(event);
        System.out.println("Event added successfully.");
        System.out.println("Event Details:\n\t Name : " + event.getEventName() + "\n\tDate: " + event.getLocalDateTime() +
                "\n\tLocation: " + event.getLocation() + "\n\tPrice: " + event.getPrice());
    }
}

