package com.Real_Time_Event_Ticketing_System.backend.CLI;
import com.Real_Time_Event_Ticketing_System.backend.Models.Event;
import com.Real_Time_Event_Ticketing_System.backend.Services.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeParseException;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


@Component
public class CLI {
    private static final Logger logger = Logger.getLogger("");
    private TicketPool ticketPool;
    private static ExecutorService executorService;
    public static final Event event = new Event("IIT Career Fair 2025",LocalDateTime.of(2025, 05, 10, 8, 30, 00),"BMICH",2500.0 );
    private Scanner input = new Scanner(System.in);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void run() throws Exception {
        System.out.println("Starting application...");
        System.out.println("Welcome to the Real Time Event Ticketing System!");
        System.out.println("Event Details:\n\tEvent Name: "+event.getEventName()+"\n\tEvent Date and Time: "+event.getLocalDateTime()+"\n\tEvent Location: "+event.getLocation()+"\n\tEvent Fee: "+event.getPrice());
        systemMenu();
    }

    public void systemMenu(){
        boolean menuRunning = true;
        while (menuRunning) {
            line();
            System.out.println("--------------System Menu---------------");
            System.out.println("\t1. New Event\n\t2. Update Event\n\t3. Remove customer data from database\n\t4. Exit");
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
                    deleteDB();
                    break;
                case 4:
                    menuRunning = false;
                    System.exit(0);
                    break;
            }
        }
    }



    public void updateEvent() {
        System.out.print("--Update Event--");
        System.out.println("What do you want to update for this event?");
        System.out.println("1. Name\n2. Location\n3. Date\n4. Ticket Price");
        String choice = input.nextLine();
        String fieldToUpdate;
        switch (choice) {
            case "1":
                fieldToUpdate = "name";
                System.out.print("Enter new value for " + fieldToUpdate + ": ");

                break;
            case "2":
                fieldToUpdate = "location";
                System.out.print("Enter new value for " + fieldToUpdate + ": ");
                break;
            case "3":
                fieldToUpdate = "date";
                System.out.print("Enter new value for " + fieldToUpdate + ": ");
                break;
            case "4":
                fieldToUpdate = "ticketPrice";
                System.out.print("Enter new value for " + fieldToUpdate + ": ");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                return;
        }
        System.out.print("Enter new value for " + fieldToUpdate + ": ");
        String newValue = input.nextLine();
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

        Event event = new Event(eventName, eventDate, eventLocation, ticketPrice);
        System.out.println("Event added successfully.");
        System.out.println("Event Details:\n\tName     : " + event.getEventName() + "\n\tDate     : " + event.getLocalDateTime() +
                "\n\tLocation : " + event.getLocation() + "\n\tPrice    : " + event.getPrice());
    }

    private void deleteDB(){
        String sql = "DROP TABLE ticket_database.customer_details;";
        jdbcTemplate.update(sql);
        System.out.println("Database table deleted successfully.\n you have to restart the application.");
        System.exit(0);
    }

    private void line(){
        System.out.println();
        for(int i = 0;i<40;i++){
            System.out.print("-");
        }
        System.out.println();

    }
}

