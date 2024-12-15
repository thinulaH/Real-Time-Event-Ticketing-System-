import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class MainCLI {
    private static final Logger logger = LoggerConfig.getLogger(MainCLI.class.getName());
    private static TicketPool ticketPool;
    static boolean isRunning = false;
    private static ExecutorService executorService;

    public static void main(String[] args) {
        logger.info("Starting the application...");
        Scanner input = new Scanner(System.in);
        System.out.println("\n\n\n=======================================================\n" +
                "   Welcome to the Real-Time Event Ticketing System  \n" +
                "=======================================================\n" +
                "Manage ticketing operations seamlessly and in real-time.\n" +
                "Features: \n" +
                "1. Load existing configuration or set up new tickets.\n" +
                "2. Start and stop ticket operations with ease.\n" +
                "3. Real-time ticket purchase and distribution.\n" +
                "\nPlease follow the prompts to begin.\n" +
                "=======================================================");
        while (true){
            System.out.println("Load existing configuration? (Y/N) \n" +
                    "Y - Load from file, N - Set up manually.");
            String loadFromFile = input.nextLine();
            if (loadFromFile.equalsIgnoreCase("Y")) {
                logger.info("Loading the configuration file...");
                loadFromJson();
                break;
            }else if(loadFromFile.equalsIgnoreCase("N")){
                setupConfiguration(input);
                break;
            }
        }
        while (true) {
            System.out.println("\n=============================================\n" +
                    "         Main Menu - Select an Option\n" +
                    "=============================================\n" +
                    "1. Start Ticket Operations  \n" +
                    "   (Begin real-time ticket management operations.)  \n" +
                    "2. Stop Ticket Operations  \n" +
                    "   (Pause all ticketing operations currently running.)  \n" +
                    "3. Exit  \n" +
                    "   (Save any progress and close the system.)");
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
                    System.out.println("Exiting the Real-Time Event Ticketing System...  \n" +
                            "Thank you for using our service! See you next time!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option selected. Please enter 1, 2, or 3.");
            }
        }
    }

    private static void setupConfiguration(Scanner scanner) {
        System.out.println("Please provide the following details to configure the system:");
        System.out.print("- Total Tickets : ");
        int totalTickets = validInput(scanner);
        System.out.print("- Ticket Release Rate (in seconds) : ");
        int ticketReleaseRate = validInput(scanner);
        System.out.print("- Customer Retrieval Rate (in seconds) : ");
        int customerRetrievalRate = validInput(scanner);
        System.out.print("- Max Ticket Capacity : ");
        int maxTicketCapacity = validInput(scanner);

        // making configuration object
        Configuration configuration = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
        //save configuration file into a json file
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("Config.json"),configuration);
            System.out.println("Configuration saved successfully! Your settings are: \n- Total Tickets: "+totalTickets +
                    "\n- Ticket Release Rate: "+ticketReleaseRate+" seconds \n- Customer Retrieval Rate: "+customerRetrievalRate+" seconds \n- Max Ticket Capacity: "+maxTicketCapacity+"\n");
        } catch (IOException e) {
            System.err.println("Error saving configuration file. "+e.getMessage());
        }

        ticketPool = new TicketPool(totalTickets, maxTicketCapacity);
        ticketPool.setReleaseRate(ticketReleaseRate);
        ticketPool.setRetrievalRate(customerRetrievalRate);
        logger.info("Configuration set up successfully.");
    }

    private static void loadFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Configuration configuration = objectMapper.readValue(new File("Config.json"), Configuration.class);

            // Extract and set the parameters
            int totalTickets = configuration.getTotalTickets();
            int ticketReleaseRate = configuration.getTicketReleaseRate();
            int customerRetrievalRate = configuration.getCustomerRetrivalRate();
            int maxTicketCapacity = configuration.getMaxTicketCapacity();

            // Initialize the TicketPool with the loaded parameters
            ticketPool = new TicketPool(totalTickets, maxTicketCapacity);
            ticketPool.setReleaseRate(ticketReleaseRate);
            ticketPool.setRetrievalRate(customerRetrievalRate);
            logger.info("Configuration loaded successfully from Config.json.");

            System.out.println("Configuration loaded successfully.");
            System.out.println("\tTotal tickets: " + totalTickets+"\n\tTicket Release Rate: "+ticketReleaseRate+"\n\tCustomer Retrieval Rate: "+customerRetrievalRate+"\n\tMax Ticket Capacity: "+maxTicketCapacity);

        } catch (IOException e) {
            logger.severe("Failed to load configuration from file: " + e.getMessage());
            System.err.println("Error loading configuration file: " + e.getMessage());
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
        logger.info("Ticket operations started.");
        isRunning = true;
        executorService = Executors.newFixedThreadPool(4);
        executorService.execute(new Vendor(ticketPool,"Vendor 1"));
        executorService.execute(new Vendor(ticketPool,"Vendor 2"));
        executorService.execute(new Customer(ticketPool,"Customer 1"));
        executorService.execute(new Customer(ticketPool,"Customer 2"));
    }

    private static void stopTicketingOperation() {
        isRunning = false;
        System.out.println("Stopping ticket operations...");
        logger.info("Stopping ticket operations...");
        executorService.shutdownNow();
        logger.info("Vendor and customer threads stopped.");

    }
}