export interface SignInResponse {
  token: string;
  role: string;
  expiresIn: number;
}

export interface SignInRequest {
  username: string;
  password: string;
}
