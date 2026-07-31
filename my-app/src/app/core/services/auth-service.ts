import { inject, Service, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { SignInRequest, SignInResponse } from '../model/user.model';

@Service()
export class AuthService {
  private http = inject(HttpClient);
  private readonly API_URL = 'http://localhost:8080/api/v1/auth';
  private authenticated = signal<boolean>(this.hasToken());

  signIn(request: SignInRequest): Observable<SignInResponse> {
    return this.http.post<SignInResponse>(`${this.API_URL}/signin`, request).pipe(
      tap((response: SignInResponse) => {
        if (response && response.token) {
          localStorage.setItem('token', response.token);
          this.authenticated.set(true);
        }
      }),
    );
  }

  signOut(): void {
    localStorage.removeItem('token');
    this.authenticated.set(false);
  }

  hasToken(): boolean {
    return !!localStorage.getItem('token');
  }

  isAuthenticated(): boolean {
    return this.authenticated.asReadonly()();
  }

  // login(): Observable<any> {
  //   return this.http.post<Observable<any>>('http://localhost:3000/auth/login', {
  //     username: 'cristi',
  //     password: 'cristi123',
  //   });
  // }
  //
  // register(): Observable<any> {
  //   return this.http.post<Observable<any>>('http://localhost:3000/auth/register', {
  //     username: 'cristi',
  //     password: 'cristi123',
  //     firstName: 'cristi',
  //     lastName: 'czika',
  //     email: 'cristi@gmail.com',
  //   });
  // }
}
