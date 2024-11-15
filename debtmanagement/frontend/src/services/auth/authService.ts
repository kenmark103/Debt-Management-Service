import { AuthResponse, LoginRequest } from '../../types/auth.types';
import axiosInstance from '../api/axios';

const API_URL = 'http://localhost:8080/auth'; // Using API Gateway URL

  
  export const authService = {
    login: async (credentials: LoginRequest): Promise<AuthResponse> => {
        const response = await axiosInstance.post(`${API_URL}/login`, credentials);
        return response.data;
    },
  
    isAuthenticated: (): boolean => {
      return !!localStorage.getItem('token');
    },

  register: async (userData: LoginRequest): Promise<AuthResponse> => {
    const response = await fetch(`${API_URL}/registered`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userData),
    });

    if (!response.ok) {
      throw new Error('Registration failed');
    }

    return response.json();
  },

  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  }
};