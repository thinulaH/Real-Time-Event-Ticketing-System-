import React, { useState, useEffect } from 'react';
import { ApiService } from '../services/ApiService';
import { SystemConfiguration } from '../models/SystemConfiguration';
import { Button } from './ui/button';
import { Input } from './ui/input';
import { Card, CardContent, CardHeader, CardTitle } from './ui/card';

export const ConfigurationForm: React.FC = () => {
  const [config, setConfig] = useState<SystemConfiguration>({
    totalTickets: 0,
    ticketReleaseRate: 0,
    customerRetrievalRate: 0,
    maxTicketCapacity: 0
  });

  useEffect(() => {
    const fetchConfig = async () => {
      try {
        const currentConfig = await ApiService.getConfiguration();
        setConfig(currentConfig);
      } catch (error) {
        console.error('Failed to fetch configuration', error);
      }
    };
    fetchConfig();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await ApiService.updateConfiguration(config);
      alert('Configuration updated successfully!');
    } catch (error) {
      console.error('Failed to update configuration', error);
    }
  };

  return (
    <Card className="w-full max-w-md">
      <CardHeader>
        <CardTitle>System Configuration</CardTitle>
      </CardHeader>
      <CardContent>
        <form onSubmit={handleSubmit} className="space-y-4">
          <Input
            type="number"
            placeholder="Total Tickets"
            value={config.totalTickets}
            onChange={(e) => setConfig({...config, totalTickets: Number(e.target.value)})}
          />
          <Input
            type="number"
            placeholder="Ticket Release Rate"
            value={config.ticketReleaseRate}
            onChange={(e) => setConfig({...config, ticketReleaseRate: Number(e.target.value)})}
          />
          <Input
            type="number"
            placeholder="Customer Retrieval Rate"
            value={config.customerRetrievalRate}
            onChange={(e) => setConfig({...config, customerRetrievalRate: Number(e.target.value)})}
          />
          <Input
            type="number"
            placeholder="Maximum Ticket Capacity"
            value={config.maxTicketCapacity}
            onChange={(e) => setConfig({...config, maxTicketCapacity: Number(e.target.value)})}
          />
          <Button type="submit" className="w-full">Update Configuration</Button>
        </form>
      </CardContent>
    </Card>
  );
};