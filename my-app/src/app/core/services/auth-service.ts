import { inject, Service, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { SignInRequest, SignInResponse } from '../model/user.model';

@Service()
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly API_URL =
    'https://czika-cristian-backend.happyriver-3aa78494.germanywestcentral.azurecontainerapps.io/api/v1/auth';
  private readonly authenticated = signal<boolean>(this.hasToken());
  readonly isAuthenticated = this.authenticated.asReadonly();

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

  getToken(): string {
    return localStorage.getItem('token') ?? 'demo-token';
  }

  signOut(): void {
    localStorage.removeItem('token');
    this.authenticated.set(false);
  }

  hasToken(): boolean {
    return !!localStorage.getItem('token');
  }
}
