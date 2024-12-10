export interface SystemConfiguration {
    totalTickets: number;
    ticketReleaseRate: number;
    customerRetrievalRate: number;
    maxTicketCapacity: number;
  }
  
  export interface CustomerDetails {
    id?: number;
    name: string;
    email: string;
    phoneNo: string;
  }