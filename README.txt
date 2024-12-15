
#Name : Thinula Harischandra
#IIT  : 20231158
#UOW  : w2051872

Real-Time-Event-Ticketing-System

Introduction 

This Real-Time-Event-Ticketing-System is a user-friendly application designed to streamline creating, tracking, and managing tickets for various use cases. This system supports effective ticket handling with a simple and intuitive interface, making it ideal for individuals and teams looking to manage tasks or support requests. This is made for a one-event ticketing.

Setup Instruction 

Prerequisites
Before setting up the application, ensure that the following prerequisites are installed on your system:

Node.js : Version 16.x or higher
npm : comes with Node.js
Vite: Installed globally through npm
Backend: Java Development Kit (JDK) 17+
Database: MySql
Intellij Idea

Building and running the Application

1. Clone the Repository:
	git clone https://github.com/thinulaH/Real-Time-Event-Ticketing-System-
	Go to the folder in the terminal

---Frontend---

2, Install Dependencies
	npm install
3. Start the Application
	npm run dev
	Open the application in your browser at:
	http://localhost:5173

---Backend---

4. open the /backend on the IntelliJ 
5. Run the spring boot application

* You can run the CLI part separately as a java file

UI Controls 

---Frontend---

Admin Login 
	Log in as a admin (Default password: admin) 
	configure the system by giveing the configuration parameters
		Total Number of Tickets (totalTickets)
		Ticket Release Rate (ticketReleaseRate)
		Customer Retrieval Rate (customerRetrievalRate)
		Maximum Ticket Capacity (maxTicketCapacity)
	View customer details - You can view the ticket bought customer details
Book Ticket
	Customers can buy a ticket by giving their name, email and phone number

--Backend--

Event Details update
	Can update the event details of the system

--CLI--

In the CLI part can simulate ticket buying and selling with two customers and two vendors

Optional Advanced Functionalities:
	Saving customer buying details in the database
	Used ReentrantLock 

Feedback and Support

If you encounter any issues or have suggestions for improvement, feel free to raise an issue on the repository or contact me directly at thinula.20231158@iit.ac.lk


		



	