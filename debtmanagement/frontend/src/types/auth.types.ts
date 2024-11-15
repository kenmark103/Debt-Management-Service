export interface AuthResponse {
    token: string;
    message: string;
  }
  
  export interface LoginRequest {
    username: string;
    password: string;
  }
  
  export interface User {
    username: string;
    role: string;
  }