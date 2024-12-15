package com.Real_Time_Event_Ticketing_System.backend.Config;

import java.io.IOException;
import java.util.logging.*;

public class LoggerConfig {
    private static final String LOG_FILE_NAME = "app_logs.txt";
    private static final Logger logger = Logger.getLogger("");

    static {
        try {
            // Remove default console handlers
            Logger rootLogger = Logger.getLogger("");
            for (Handler handler : rootLogger.getHandlers()) {
                rootLogger.removeHandler(handler);
            }

            // Set up a file handler
            FileHandler fileHandler = new FileHandler(LOG_FILE_NAME, true); // Append mode
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.INFO);
            rootLogger.addHandler(fileHandler);

            // Set up a console handler
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());
            consoleHandler.setLevel(Level.INFO);
            rootLogger.addHandler(consoleHandler);

            // Set the overall logging level
            rootLogger.setLevel(Level.INFO);

        } catch (IOException e) {
            System.err.println("Failed to set up logging: " + e.getMessage());
        }
    }

    public static Logger getLogger(String className) {
        return Logger.getLogger(className);
    }
}