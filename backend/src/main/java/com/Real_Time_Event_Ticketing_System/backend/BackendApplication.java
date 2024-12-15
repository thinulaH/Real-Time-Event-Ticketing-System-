package com.Real_Time_Event_Ticketing_System.backend;

import com.Real_Time_Event_Ticketing_System.backend.CLI.CLI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.Real_Time_Event_Ticketing_System"})
public class BackendApplication {
	public static void main(String[] args) throws Exception {
		// Start the Spring Application and retrieve the ApplicationContext
		ApplicationContext context = SpringApplication.run(BackendApplication.class, args);

		try {
			// Get the CLI bean from the Spring context
			CLI cli = context.getBean(CLI.class);
			cli.run();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

