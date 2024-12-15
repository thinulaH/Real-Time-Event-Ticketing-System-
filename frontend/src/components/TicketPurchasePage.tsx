import React, { useState } from 'react';
import { apiService } from '../services/apiService';

export const TicketPurchasePage: React.FC = () => {
    const [customerDetails, setCustomerDetails] = useState({
        name: '',
        email: '',
        phoneNumber: ''
    });
    const [message, setMessage] = useState<string>('');
    const [errors, setErrors] = useState({
        name: '',
        email: '',
        phoneNumber: ''
    });

    // Input change handler
    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setCustomerDetails(prev => ({
            ...prev,
            [name]: value
        }));
    };

    // Validation functions
    const validateEmail = (email: string) => {
        // Simple email validation regex
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        return emailRegex.test(email);
    };

    const validatePhoneNumber = (phoneNumber: string) => {
        // Phone number should be a string containing only digits
        const phoneRegex = /^[0-9]+$/;
        return phoneRegex.test(phoneNumber);
    };

    const handlePurchaseTicket = async () => {
        let valid = true;
        let errorMessages = {
            name: '',
            email: '',
            phoneNumber: ''
        };

        // Validate Name (no specific validation needed)
        if (customerDetails.name.trim() === '') {
            errorMessages.name = 'Name is required';
            valid = false;
        }

        // Validate Email
        if (!validateEmail(customerDetails.email)) {
            errorMessages.email = 'Please enter a valid email (format: example@email.com)';
            valid = false;
        }

        // Validate Phone Number
        if (!validatePhoneNumber(customerDetails.phoneNumber)) {
            errorMessages.phoneNumber = 'Please enter a valid phone number (format: 071-1234567)';
            valid = false;
        }

        // If validation fails, set the error messages and exit
        if (!valid) {
            setErrors(errorMessages);
            return;
        }

        // If validation passes, reset error messages
        setErrors({
            name: '',
            email: '',
            phoneNumber: ''
        });

        try {
            const result = await apiService.purchaseTicket(customerDetails);
            setMessage(result);
            // Reset form after successful purchase
            setCustomerDetails({
                name: '',
                email: '',
                phoneNumber: ''
            });
        } catch (error) {
            setMessage('Failed to purchase ticket');
        }
    };

    return (
        <div className="p-4 max-w-md mx-auto bg-white rounded-md">
            <h1 className="text-2xl mb-4">Book Your Ticket</h1>
            <div className="space-y-4">
                {/* Name Field */}
                <input
                    type="text"
                    name="name"
                    value={customerDetails.name}
                    onChange={handleInputChange}
                    placeholder="Full Name"
                    className="w-full p-2 border rounded"
                />
                {errors.name && <p className="text-red-500 text-sm">{errors.name}</p>}

                {/* Email Field */}
                <input
                    type="email"
                    name="email"
                    value={customerDetails.email}
                    onChange={handleInputChange}
                    placeholder="Email Address"
                    className="w-full p-2 border rounded"
                />
                {errors.email && <p className="text-red-500 text-sm">{errors.email}</p>}

                {/* Phone Number Field */}
                <input
                    type="tel"
                    name="phoneNumber"
                    value={customerDetails.phoneNumber}
                    onChange={handleInputChange}
                    placeholder="Phone Number"
                    className="w-full p-2 border rounded"
                />
                {errors.phoneNumber && <p className="text-red-500 text-sm">{errors.phoneNumber}</p>}

                {/* Submit Button */}
                <button 
                    onClick={handlePurchaseTicket}
                    className="w-full bg-gray-900 text-white hover:bg-gray-600 p-2 rounded"
                >
                    Purchase Ticket
                </button>

                {/* Success or Error Message */}
                {message && <p className="mt-4 text-center">{message}</p>}
            </div>
        </div>
    );
};