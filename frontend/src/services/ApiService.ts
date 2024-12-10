import axios from 'axios';
import { SystemConfiguration, CustomerDetails } from '../models/SystemConfiguration';

const BASE_URL = 'http://localhost:8080';

export const ApiService = {
  getConfiguration: async () => {
    const response = await axios.get<SystemConfiguration>(`${BASE_URL}/config/get-config`);
    return response.data;
  },

  updateConfiguration: async (config: SystemConfiguration) => {
    const response = await axios.post(`${BASE_URL}/config/set-config`, config);
    return response.data;
  },

  startSystem: async () => {
    const response = await axios.post(`${BASE_URL}/config/start`);
    return response.data;
  },

  stopSystem: async () => {
    const response = await axios.post(`${BASE_URL}/config/stop`);
    return response.data;
  },

  addTicket: async (customerDetails: Omit<CustomerDetails, 'id'>) => {
    const response = await axios.post(`${BASE_URL}/add-Ticket`, null, {
      headers: {
        Name: customerDetails.name,
        Email: customerDetails.email,
        'Phone-Number': customerDetails.phoneNo
      }
    });
    return response.data;
  },

  getTickets: async () => {
    const response = await axios.get<CustomerDetails[]>(`${BASE_URL}/get-Ticket`);
    return response.data;
  }
};