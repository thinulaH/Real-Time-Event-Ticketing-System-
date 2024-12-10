package com.Real_Time_Event_Ticketing_System.backend.CLI;
import com.Real_Time_Event_Ticketing_System.backend.Customer;
import com.Real_Time_Event_Ticketing_System.backend.Database.CustomerDetailsRepository;
import com.Real_Time_Event_Ticketing_System.backend.Database.EventRepository;
import com.Real_Time_Event_Ticketing_System.backend.Models.Event;
import com.Real_Time_Event_Ticketing_System.backend.Services.EventService;
import com.Real_Time_Event_Ticketing_System.backend.Services.TicketPool;
import com.Real_Time_Event_Ticketing_System.backend.Vendor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeParseException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import java.util.concurrent.locks.ReentrantLock;

import static com.Real_Time_Event_Ticketing_System.backend.Services.EventConfigurationManager.isRunning;

@Component
public class CLI {
    private static final Logger logger = Logger.getLogger("");
    private final CustomerDetailsRepository customerDetailsRepository;
    private TicketPool ticketPool;
    private static ExecutorService executorService;

    private final EventRepository eventRepository;
    private final EventService eventService;
    private Scanner input = new Scanner(System.in);

    private final ReentrantLock configLock = new ReentrantLock();  // ReentrantLock for configuration

    public CLI(EventService eventService, EventRepository eventRepository, CustomerDetailsRepository customerDetailsRepository) {
        this.eventService = eventService;
        this.eventRepository = eventRepository;
        this.customerDetailsRepository = customerDetailsRepository;
    }


    public void run(String... args) throws Exception {
        if (args.length > 0) {
            String command = args[0];
            logger.info("Received command: " + command);  // Log the command
            switch (command) {
                case "startApplication":
                    System.out.println("Starting application...");
                    System.out.println("Welcome to the Real Time Event Ticketing System!");
                    systemMenu();
                case "addEvent":
                    addEvent();
                    break;
                case "deleteEvent":
                    deleteEvent();
                    break;
                case "updateEvent":
                    updateEvent();
                    break;
//                case "systemConfiguration":
//                    systemConfiguration(input);
//                    runConfiguration();
//                    break;
                default:
                    logger.warning("Invalid command: " + command);
            }
        } else {
            logger.warning("No arguments provided.");
        }
    }
    public void systemMenu(){
        boolean menuRunning = true;
        while (menuRunning) {
            line();
            System.out.println("--------------System Menu---------------");
            System.out.println("\t1. New Event\n\t2. Update Event\n\t3. Delete Event\n\t4. Exit");
            line();
            System.out.print("Enter your choice: ");

            int menuOption = input.nextInt();
            switch (menuOption) {
                case 1:
                    addEvent();
                    break;
                case 2:
                    updateEvent();
                    break;
                case 3:
                    deleteEvent();
                    break;
                case 4:
                    menuRunning = false;
                    System.out.println("Exiting application...");
                    System.exit(0);
                    break;
            }

        }

    }

//    private void runConfiguration() {
//        while (true) {
//            System.out.println("Select an option: \n\t1 - Start\n\t2 - Stop\n\t3 - Exit");
//            String option = input.next();
//
//            switch (option) {
//                case "1":
//                    if (!isRunning) {
//                        //startTicketingOperation();
//                    } else {
//                        System.out.println("System is already running.");
//                    }
//                    break;
//                case "2":
//                    if (isRunning) {
//                        //stopTicketingOperation();
//                    } else {
//                        System.out.println("Operations are not running.");
//                    }
//                    break;
//                case "3":
//                    //stopTicketingOperation();
//                    System.out.println("Exiting program...");
//                    System.exit(0);
//                default:
//                    System.out.println("Invalid option. Please try again.");
//            }
//        }
//    }

//    private void systemConfiguration(Scanner scanner) {
//        System.out.println("Enter event ID: ");
//        int eventID = validInput(scanner);
//        System.out.print("Enter total tickets: ");
//        int totalTickets = validInput(scanner);
//        System.out.print("Enter ticket release rate (in seconds): ");
//        int ticketReleaseRate = validInput(scanner);
//        System.out.print("Enter customer retrieval rate (in seconds): ");
//        int customerRetrievalRate = validInput(scanner);
//        System.out.print("Enter max ticket capacity: ");
//        int maxTicketCapacity = validInput(scanner);
//
//        // Locking the configuration block
//        configLock.lock();
//        try {
//            ticketPool = new TicketPool(totalTickets, maxTicketCapacity);  // Initialize ticketPool
//            ticketPool.setReleaseRate(ticketReleaseRate);
//            ticketPool.setEventID(eventID);
//            ticketPool.setRetrievalRate(customerRetrievalRate);
//            ticketPool.setCustomerDetailsRepository(customerDetailsRepository);
//        } finally {
//            configLock.unlock();  // Unlock once configuration is done
//        }
//    }
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

//    private static int validInput(Scanner scanner) {
//        int input;
//        while (true) {
//            try {
//                if (scanner.hasNextLine()) {
//                    scanner.nextLine();
//                }
//
//                input = Integer.parseInt(scanner.nextLine());
//                if (input > 0) {
//                    break;
//                } else {
//                    System.out.println("Please enter a positive integer.");
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid input. Please enter a positive integer.");
//            }
//        }
//        return input;
//    }

//    private void startTicketingOperation() {
//        System.out.println("Starting ticket Operations...");
//        isRunning = true;
//        executorService = Executors.newFixedThreadPool(2);
//        executorService.execute(new Vendor(ticketPool));
//        executorService.execute(new Customer(ticketPool));
//    }
//
//    private void stopTicketingOperation() {
//        if (isRunning) {
//            System.out.println("Stopping ticket Operations...");
//            isRunning = false;  // Signal to stop
//            executorService.shutdownNow(); // Immediately shutdown the executor service
//            logger.info("Stopping vendor and customer threads...");
//        }
//    }

    private void deleteEvent() {
        System.out.print("Enter event ID to delete: ");
        Long eventID = input.nextLong();
        if (eventRepository.existsById(eventID)) {
            System.out.println("Event with ID " + eventID + " has been deleted.");
            eventRepository.deleteById(eventID);
        } else {
            System.out.println("No event found with ID " + eventID);
        }
    }
    @Transactional
    public void updateEvent() {
        System.out.print("Enter event ID to update: ");
        Long eventID = input.nextLong();
        input.nextLine();
        System.out.println("What do you want to update for this event?");
        System.out.println("1. Name\n2. Location\n3. Maximum Capacity\n4. Date\n5. Ticket Price");

        String choice = input.nextLine();
        String fieldToUpdate;

        switch (choice) {
            case "1":
                fieldToUpdate = "name";
                break;
            case "2":
                fieldToUpdate = "location";
                break;
            case "3":
                fieldToUpdate = "capacity";
                break;
            case "4":
                fieldToUpdate = "date";
                break;
            case "5":
                fieldToUpdate = "ticketPrice";
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                return;
        }

        System.out.print("Enter new value for " + fieldToUpdate + ": ");
        String newValue = input.nextLine();
        eventService.updateEvent(eventID, fieldToUpdate, newValue);
    }

    private void addEvent() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter event name: ");
        String eventName = input.nextLine();

        String eventDateStr = "";
        LocalDateTime eventDate = null;
        while (eventDate == null) {
            System.out.print("Enter event date (yyyy-MM-dd HH:mm): ");
            eventDateStr = input.nextLine();

            try {
                eventDate = LocalDateTime.parse(eventDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use 'yyyy-MM-dd HH:mm'.");
            }
        }
        System.out.print("Enter event location:");
        String eventLocation = input.nextLine();

        System.out.print("Enter ticket price: ");
        double ticketPrice = input.nextDouble();

        System.out.print("Enter maximum capacity: ");
        int maximumCapacity = validInput(input);

        Event event = new Event(eventName, eventDate, eventLocation, ticketPrice,maximumCapacity);
        eventService.saveEvent(event);
        System.out.println("Event added successfully.");
        System.out.println("Event Details:\n\tEventID  : "+ event.getEventID()+"\n\tName     : " + event.getEventName() + "\n\tDate     : " + event.getLocalDateTime() +
                "\n\tLocation : " + event.getLocation() + "\n\tPrice    : " + event.getPrice());
    }
    private void line(){
        System.out.println();
        for(int i = 0;i<40;i++){
            System.out.print("-");
        }
        System.out.println();

    }
}

