import React from 'react';
import { ConfigurationForm } from './components/ConfigurationForm';
import { SystemControls } from './components/SystemControls';
import { CustomerTicketTable } from './components/CustomerTicketTable';

const App: React.FC = () => {
  return (
    <div className="container mx-auto p-6 space-y-6">
      <h1 className="text-3xl font-bold text-center mb-6">Real-Time Event Ticketing System</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <ConfigurationForm />
        <SystemControls />
      </div>
      <CustomerTicketTable />
    </div>
  );
};

export default App;