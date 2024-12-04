package com.Real_Time_Event_Ticketing_System.backend.CLI;

import com.Real_Time_Event_Ticketing_System.backend.BackendApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;


public class MainCLI {
    ApplicationContext context = SpringApplication.run(BackendApplication.class);
    private static final Logger logger = Logger.getLogger("");
    private static TicketPool ticketPool;
    static boolean isRunning = false;
    private static ExecutorService executorService;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Real-Time Event Ticketing System ");
        setupConfiguration(input);

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

    private static void setupConfiguration(Scanner scanner) {
        System.out.print("Enter total tickets: ");
        int totalTickets = validInput(scanner);
        System.out.print("Enter ticket release rate (in seconds): ");
        int ticketReleaseRate = validInput(scanner);
        System.out.print("Enter customer retrieval rate (in seconds): ");
        int customerRetrievalRate = validInput(scanner);
        System.out.print("Enter max ticket capacity: ");
        int maxTicketCapacity = validInput(scanner);

        // Initialize TicketPool directly
        ticketPool = new TicketPool(totalTickets, maxTicketCapacity);
        ticketPool.setReleaseRate(ticketReleaseRate);
        ticketPool.setRetrievalRate(customerRetrievalRate);

        // Save configuration to JSON file
        Configuration configuration = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("Config.json"), configuration);
            System.out.println("Configuration saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving configuration: " + e.getMessage());
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

    private static void startTicketingOperation() {
        System.out.println("Starting ticket operations...");
        isRunning = true;
        executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Vendor(ticketPool));
        executorService.execute(new Customer(ticketPool));
    }

    private static void stopTicketingOperation() {
        if (isRunning) {
            System.out.println("Stopping ticket operations...");
            isRunning = false;  // Signal to stop
            executorService.shutdownNow(); // Immediately shutdown the executor service
            logger.info("Stopping vendor and customer threads...");
        }
    }
}

