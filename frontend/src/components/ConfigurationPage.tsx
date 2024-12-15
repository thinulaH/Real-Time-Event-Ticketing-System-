import React, { useState, useEffect } from 'react';
import { apiService } from '../services/apiService';
import { SystemConfiguration } from '../types/types';

export const ConfigurationPage: React.FC = () => {
    const [config, setConfig] = useState<SystemConfiguration>({
        totalTickets: 1,
        ticketReleaseRate: 1,
        customerRetrievalRate: 1,
        maxTicketCapacity: 1,
    });
    const [message, setMessage] = useState<string>('');
    const [isUpdateDisabled, setIsUpdateDisabled] = useState<boolean>(false);

    useEffect(() => {
        const fetchConfig = async () => {
            try {
                const currentConfig = await apiService.getConfiguration();
                setConfig(currentConfig);
            } catch (error) {
                setMessage('Failed to fetch configuration');
            }
        };
        fetchConfig();
    }, []);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setConfig(prev => ({
            ...prev,
            [name]: Number(value),
        }));
        setIsUpdateDisabled(false); // Re-enable button if input changes
    };

    const handleUpdateConfig = async () => {
        // Validate that all inputs are greater than 0
        if (
            config.totalTickets <= 0 ||
            config.ticketReleaseRate <= 0 ||
            config.customerRetrievalRate <= 0 ||
            config.maxTicketCapacity <= 0
        ) {
            setMessage('All values must be greater than 0');
            return;
        }

        try {
            const result = await apiService.updateConfiguration(config);
            setMessage(result);
            setIsUpdateDisabled(true); // Disable button after success
        } catch (error) {
            setMessage('Failed to update configuration');
        }
    };

    const handleSystemControl = async (action: 'start' | 'stop') => {
        try {
            const result =
                action === 'start'
                    ? await apiService.startSystem()
                    : await apiService.stopSystem();
            setMessage(result);
        } catch (error) {
            setMessage(`Failed to ${action} system`);
        }
    };

    return (
        <div className="p-4 max-w-md mx-auto bg-white rounded-md">
            <h1 className="text-2xl mb-4">System Configuration</h1>
            <div className="space-y-4">
                {/* Input Rows */}
                {[
                    { name: 'totalTickets', label: 'Total Tickets', value: config.totalTickets },
                    { name: 'ticketReleaseRate', label: 'Ticket Release Rate', value: config.ticketReleaseRate },
                    { name: 'customerRetrievalRate', label: 'Customer Retrieval Rate', value: config.customerRetrievalRate },
                    { name: 'maxTicketCapacity', label: 'Max Ticket Capacity', value: config.maxTicketCapacity },
                ].map(({ name, label, value }) => (
                    <div key={name} className="flex items-center space-x-4">
                        <label htmlFor={name} className="w-1/3 text-right font-medium">
                            {label}:
                        </label>
                        <input
                            type="number"
                            id={name}
                            name={name}
                            value={value}
                            onChange={handleInputChange}
                            placeholder={label}
                            className="w-2/3 p-2 border rounded"
                        />
                    </div>
                ))}

                {/* Buttons */}
                <div className="flex space-x-2">
                    <button
                        onClick={handleUpdateConfig}
                        disabled={isUpdateDisabled} // Disable button based on state
                        className={`p-2 rounded flex-1 ${
                            isUpdateDisabled ? 'bg-gray-400 cursor-not-allowed' : 'bg-blue-500 text-white'
                        }`}
                    >
                        Update Configuration
                    </button>
                </div>
                <div className="flex space-x-2">
                    <button
                        onClick={() => handleSystemControl('start')}
                        className="bg-green-500 text-white p-2 rounded flex-1"
                    >
                        Start System
                    </button>
                    <button
                        onClick={() => handleSystemControl('stop')}
                        className="bg-red-500 text-white p-2 rounded flex-1"
                    >
                        Stop System
                    </button>
                </div>

                {/* Message */}
                {message && <p className="mt-4 text-center">{message}</p>}
            </div>
        </div>
    );
};