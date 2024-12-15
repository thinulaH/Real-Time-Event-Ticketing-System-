// import React, { useState } from 'react';
// import axios from 'axios';

// const TicketPurchasePage: React.FC = () => {
//   const [customerDetails, setCustomerDetails] = useState({
//     name: '',
//     email: '',
//     phoneNumber: ''
//   });

//   const handlePurchaseTicket = async () => {
//     try {
//       const response = await axios.post('http://localhost:8080/add-Ticket', null, {
//         headers: {
//           'Name': customerDetails.name,
//           'Email': customerDetails.email,
//           'Phone-Number': customerDetails.phoneNumber
//         }
//       });

//       alert(response.data);
      
//       // Reset form after successful purchase
//       setCustomerDetails({
//         name: '',
//         email: '',
//         phoneNumber: ''
//       });
//     } catch (error) {
//       console.error('Error purchasing ticket', error);
//       alert('Failed to purchase ticket');
//     }
//   };

//   return (
//     <div className="max-w-md mx-auto p-6 bg-white shadow-md rounded-lg">
//       <h2 className="text-2xl font-bold mb-6 text-center">Purchase Ticket</h2>
//       <div className="space-y-4">
//         <div>
//           <label className="block text-sm font-medium text-gray-700">Name</label>
//           <input 
//             type="text" 
//             className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm py-2 px-3"
//             value={customerDetails.name}
//             onChange={(e) => setCustomerDetails({...customerDetails, name: e.target.value})}
//             placeholder="Enter your name"
//           />
//         </div>

//         <div>
//           <label className="block text-sm font-medium text-gray-700">Email</label>
//           <input 
//             type="email" 
//             className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm py-2 px-3"
//             value={customerDetails.email}
//             onChange={(e) => setCustomerDetails({...customerDetails, email: e.target.value})}
//             placeholder="Enter your email"
//           />
//         </div>

//         <div>
//           <label className="block text-sm font-medium text-gray-700">Phone Number</label>
//           <input 
//             type="tel" 
//             className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm py-2 px-3"
//             value={customerDetails.phoneNumber}
//             onChange={(e) => setCustomerDetails({...customerDetails, phoneNumber: e.target.value})}
//             placeholder="Enter your phone number"
//           />
//         </div>

//         <button 
//           onClick={handlePurchaseTicket}
//           className="w-full bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600 transition duration-300"
//         >
//           Purchase Ticket
//         </button>
//       </div>
//     </div>
//   );
// };

// export default TicketPurchasePage;