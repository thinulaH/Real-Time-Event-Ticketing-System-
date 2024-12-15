import { SystemConfiguration, CustomerDetails } from '../types/types';

const BASE_URL = 'http://localhost:8080'; // Adjust to match your backend URL

// WebSocket connection for live ticket updates
let socket: WebSocket | null = null;

export const apiService = {
  async updateConfiguration(config: SystemConfiguration): Promise<string> {
    try {
      const response = await fetch(`${BASE_URL}/config/set-config`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(config),
      });

      if (!response.ok) {
        throw new Error('Failed to update configuration');
      }

      return response.text();
    } catch (error) {
      console.error('Configuration update error:', error);
      throw error;
    }
  },

  async getConfiguration(): Promise<SystemConfiguration> {
    try {
      const response = await fetch(`${BASE_URL}/config/get-config`);
      
      if (!response.ok) {
        throw new Error('Failed to fetch configuration');
      }

      return response.json();
    } catch (error) {
      console.error('Configuration fetch error:', error);
      throw error;
    }
  },

  async startSystem(): Promise<string> {
    try {
      const response = await fetch(`${BASE_URL}/config/start`, {
        method: 'POST',
      });

      if (!response.ok) {
        throw new Error('Failed to start system');
      }

      return response.text();
    } catch (error) {
      console.error('System start error:', error);
      throw error;
    }
  },

  async stopSystem(): Promise<string> {
    try {
      const response = await fetch(`${BASE_URL}/config/stop`, {
        method: 'POST',
      });

      if (!response.ok) {
        throw new Error('Failed to stop system');
      }

      return response.text();
    } catch (error) {
      console.error('System stop error:', error);
      throw error;
    }
  },

  async purchaseTicket(customerDetails: {
    name: string;
    email: string;
    phoneNumber: string;
  }): Promise<string> {
    try {
      const response = await fetch(`${BASE_URL}/add-Ticket`, {
        method: 'POST',
        headers: {
          'Name': customerDetails.name,
          'Email': customerDetails.email,
          'Phone-Number': customerDetails.phoneNumber,
        },
      });

      if (!response.ok) {
        throw new Error('Failed to purchase ticket');
      }

      return response.text();
    } catch (error) {
      console.error('Ticket purchase error:', error);
      throw error;
    }
  },

  async deleteAllCustomers(): Promise<string> {
    try {
      const response = await fetch(`${BASE_URL}/delete-all-customers`, {
        method: 'DELETE',
      });

      if (!response.ok) {
        throw new Error('Failed to delete customer details');
      }

      return response.text();
    } catch (error) {
      console.error('Delete customers error:', error);
      throw error;
    }
  },

  async getCustomerDetails(): Promise<CustomerDetails[]> {
    try {
      const response = await fetch(`${BASE_URL}/get-Ticket`);
      
      if (!response.ok) {
        throw new Error('Failed to fetch customer details');
      }

      return response.json();
    } catch (error) {
      console.error('Fetch customer details error:', error);
      throw error;
    }
  },

  async downloadLogs(): Promise<Blob> {
    try {
      const response = await fetch(`${BASE_URL}/download-log`, {
        method: 'GET',
      });

      if (!response.ok) {
        throw new Error('Failed to download logs');
      }

      return response.blob();
    } catch (error) {
      console.error('Log download error:', error);
      throw error;
    }},

  async testFE(test: { name: string; email?: string; phoneNumber?: string }): Promise<string> {
    try {
      const response = await fetch(`${BASE_URL}/testFE`, {
        method: 'POST',
        headers: {
          'Name': test.name,
        },
      });

      if (!response.ok) {
        throw new Error('Frontend test failed');
      }

      return response.text();
    } catch (error) {
      console.error('Frontend test error:', error);
      throw error;
    }
  },

  // WebSocket setup for real-time ticket count updates
  initializeSocket(callback: (count: number) => void): void {
    if (socket) {
      socket.close(); // Close any previous socket connection
    }

    socket = new WebSocket('ws://localhost:8080/ticket-updates'); // WebSocket connection URL

    socket.onopen = () => {
      console.log('WebSocket connected');
    };

    socket.onmessage = (event) => {
      const ticketCount = parseInt(event.data, 10);
      callback(ticketCount); // Update ticket count when message is received
    };

    socket.onerror = (error) => {
      console.error('WebSocket error:', error);
    };

    socket.onclose = () => {
      console.log('WebSocket closed');
    };
  },

  closeSocket(): void {
    if (socket) {
      socket.close();
    }
  },
  
};