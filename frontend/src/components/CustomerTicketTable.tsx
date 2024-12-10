import React, { useState, useEffect } from 'react';
import { CustomerDetails } from '../models/SystemConfiguration';
import { ApiService } from '../services/ApiService';
import { Card, CardContent, CardHeader, CardTitle } from './ui/card';
import { Button } from './ui/button';
import { Input } from './ui/input';

export const CustomerTicketTable: React.FC = () => {
  const [tickets, setTickets] = useState<CustomerDetails[]>([]);
  const [customerDetails, setCustomerDetails] = useState({
    name: '',
    email: '',
    phoneNo: ''
  });

  useEffect(() => {
    fetchTickets();
  }, []);

  const fetchTickets = async () => {
    try {
      const fetchedTickets = await ApiService.getTickets();
      setTickets(fetchedTickets);
    } catch (error) {
      console.error('Failed to fetch tickets', error);
    }
  };

  const handleAddTicket = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await ApiService.addTicket(customerDetails);
      fetchTickets();
      setCustomerDetails({ name: '', email: '', phoneNo: '' });
    } catch (error) {
      console.error('Failed to add ticket', error);
    }
  };

  return (
    <div className="space-y-4">
      <Card>
        <CardHeader>
          <CardTitle>Add New Ticket</CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleAddTicket} className="space-y-3">
            <Input
              placeholder="Name"
              value={customerDetails.name}
              onChange={(e) => setCustomerDetails({...customerDetails, name: e.target.value})}
            />
            <Input
              placeholder="Email"
              value={customerDetails.email}
              onChange={(e) => setCustomerDetails({...customerDetails, email: e.target.value})}
            />
            <Input
              placeholder="Phone Number"
              value={customerDetails.phoneNo}
              onChange={(e) => setCustomerDetails({...customerDetails, phoneNo: e.target.value})}
            />
            <Button type="submit" className="w-full">Add Ticket</Button>
          </form>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle>Ticket List</CardTitle>
        </CardHeader>
        <CardContent>
          <table className="w-full border-collapse">
            <thead>
              <tr>
                <th className="border p-2">Name</th>
                <th className="border p-2">Email</th>
                <th className="border p-2">Phone Number</th>
              </tr>
            </thead>
            <tbody>
              {tickets.map((ticket, index) => (
                <tr key={index} className="hover:bg-gray-100">
                  <td className="border p-2">{ticket.name}</td>
                  <td className="border p-2">{ticket.email}</td>
                  <td className="border p-2">{ticket.phoneNo}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </CardContent>
      </Card>
    </div>
  );
};