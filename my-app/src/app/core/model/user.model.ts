// export interface Role {
//   id: number;
//   name: string;
// }
//
// export interface User {
//   id: number;
//   username: string;
//   email: string;
//   password: string;
//   firstName: string;
//   lastName: string;
//   role: Role;
// }

export interface SignInResponse {
  token: string;
  role: string;
  expiresIn: number;
}

export interface SignInRequest {
  username: string;
  password: string;
}
