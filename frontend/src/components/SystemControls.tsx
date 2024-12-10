import React from 'react';
import { ApiService } from '../services/ApiService';
import { Button } from './ui/button';
import { Card, CardContent, CardHeader, CardTitle } from './ui/card';

export const SystemControls: React.FC = () => {
  const handleStart = async () => {
    try {
      const response = await ApiService.startSystem();
      alert(response);
    } catch (error) {
      console.error('Failed to start system', error);
    }
  };

  const handleStop = async () => {
    try {
      const response = await ApiService.stopSystem();
      alert(response);
    } catch (error) {
      console.error('Failed to stop system', error);
    }
  };

  return (
    <Card className="w-full max-w-md">
      <CardHeader>
        <CardTitle>System Controls</CardTitle>
      </CardHeader>
      <CardContent className="flex space-x-4">
        <Button onClick={handleStart} className="w-1/2">Start System</Button>
        <Button onClick={handleStop} variant="destructive" className="w-1/2">Stop System</Button>
      </CardContent>
    </Card>
  );
};
