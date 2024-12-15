import React, { useState, useEffect } from 'react';
import { apiService } from '../services/apiService';
import { CustomerDetails } from '../types/types';

export const CustomerDetailsPage: React.FC = () => {
  const [customerDetails, setCustomerDetails] = useState<CustomerDetails[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  // Function to fetch customer details
  const fetchCustomerDetails = async () => {
    try {
      setLoading(true);
      const details = await apiService.getCustomerDetails();
      setCustomerDetails(details);
      setLoading(false);
    } catch (err) {
      setError('Failed to fetch customer details');
      setLoading(false);
    }
  };

  const downloadLogs = async () => {
    try {
      const blob = await apiService.downloadLogs();
      
      // Create a link element to trigger the download
      const link = document.createElement('a');
      link.href = URL.createObjectURL(blob);
      link.download = 'app_logs.txt';
      
      // Append to the body, click, and remove
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    } catch (err) {
      console.error('Failed to download logs', err);
      setError('Failed to download logs');
    }
  };

  // Fetch data when the component mounts
  useEffect(() => {
    fetchCustomerDetails();
  }, []);

  // Handle reload action
  const handleReload = () => {
    fetchCustomerDetails();
  };

  if (loading) return <div className="text-center">Loading...</div>;
  if (error) return <div className="text-red-500 text-center">{error}</div>;

  return (
    <div className="p-4">
      <h1 className="text-2xl mb-4 text-white">Customer Ticket Details</h1>
      {customerDetails.length === 0 ? (
        <p className="text-center bg-white rounded-md">No customer details found</p>
      ) : (
        <table className="w-full border-collapse">
          <thead>
            <tr className="bg-gray-200">
              <th className="border p-2">Ticket ID</th>
              <th className="border p-2">Name</th>
              <th className="border p-2">Email</th>
              <th className="border p-2">Phone Number</th>
              <th className="border p-2">Time</th>
            </tr>
          </thead>
          <tbody>
            {customerDetails.map((customer) => (
              <tr key={customer.id} className="bg-gray-100 hover:bg-gray-200">
                <td className="border p-2">{customer.id}</td>
                <td className="border p-2">{customer.name}</td>
                <td className="border p-2">{customer.email}</td>
                <td className="border p-2">{customer.phoneNo}</td>
                <td className="border p-2">{customer.time}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
      
      <div className="mt-4 flex justify-center space-x-4">
        <button
          onClick={handleReload}
          className="bg-blue-500 text-white px-4 py-2 rounded"
        >
          Update Table
        </button>
      </div>
    </div>
  );
};