import React, { useState, useEffect } from 'react';
import { ConfigurationPage } from './components/ConfigurationPage';
import { TicketPurchasePage } from './components/TicketPurchasePage';
import { CustomerDetailsPage } from './components/CustomerDetails';

const App: React.FC = () => {
  const [currentPage, setCurrentPage] = useState<'config' | 'purchase' | 'admin'>('purchase');
  const [adminAuthenticated, setAdminAuthenticated] = useState(false);
  const [adminPassword, setAdminPassword] = useState('');
  const [eventDetails, setEventDetails] = useState<any>(null);

  // Fetch event details from the backend
  useEffect(() => {
    const fetchEventDetails = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/event/eventDetails'); // Adjust URL as needed
        const data = await response.json();
        setEventDetails(data);
      } catch (error) {
        console.error('Error fetching event details:', error);
      }
    };

    fetchEventDetails();
  }, []);

  const handleAdminLogin = () => {
    if (adminPassword === 'admin') {
      setAdminAuthenticated(true);
      setAdminPassword('');
    } else {
      alert('Invalid Password');
    }
  };

  return (
    <div
      className="min-h-screen text-black"
      style={{
        backgroundImage: 'url(background.jpg)', // Path to your image
        backgroundSize: 'cover', // Ensure the background image covers the entire screen
        backgroundPosition: 'center', // Center the background image
        backgroundAttachment: 'fixed', // Keep the background fixed during scrolling
      }}
    >
      {/* Sticky Navbar */}
      <nav className="bg-black p-4 sticky top-0 z-10  backdrop-blur-lg">
        <div className="flex justify-between">
          <div className="text-white font-semibold">Admin Panel</div>
          <div className="flex space-x-4">
            <button
              onClick={() => setCurrentPage('purchase')}
              className={`px-4 py-2 rounded ${currentPage === 'purchase' ? 'bg-black text-white' : 'bg-gray-500 text-white'}`}
            >
              Purchase Ticket
            </button>
            <button
              onClick={() => setCurrentPage('admin')}
              className={`px-4 py-2 rounded ${currentPage === 'admin' ? 'bg-black text-white' : 'bg-gray-500 text-white'}`}
            >
              Admin Section
            </button>
          </div>
        </div>
      </nav>

      {/* Event Details Section */}
      {eventDetails && (
        <div className="text-center py-8 text-white backdrop-blur-md">
            <h2 className="text-3xl font-bold mb-4">{eventDetails.eventName}</h2>
            <p className="text-xl">--{eventDetails.location}--</p>
        </div>
      )}

      <main className="container mx-auto mt-8">
        {currentPage === 'config' && <ConfigurationPage />}
        {currentPage === 'purchase' && <TicketPurchasePage />}
        {currentPage === 'admin' && (
          <>
            {!adminAuthenticated ? (
              <div className="max-w-md mx-auto p-4  bg-white rounded-md">
                <h2 className="text-2xl mb-4">Admin Login</h2>
                <input
                  type="password"
                  placeholder="Enter Admin Password"
                  value={adminPassword}
                  onChange={(e) => setAdminPassword(e.target.value)}
                  className="w-full p-2 border rounded mb-4"
                />
                <button
                  onClick={handleAdminLogin}
                  className="bg-black hover:bg-gray-600 text-white px-4 py-2 rounded w-full"
                >
                  Login
                </button>
              </div>
            ) : (
              <div className="space-y-8">
                <ConfigurationPage />
                <CustomerDetailsPage />
              </div>
            )}
          </>
        )}
      </main>
    </div>
  );
};

export default App;