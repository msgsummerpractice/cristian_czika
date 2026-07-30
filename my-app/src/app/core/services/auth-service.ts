import { inject, Service } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Service()
export class AuthService {
  private http = inject(HttpClient);
  private authenticated = false;

  login(): Observable<any> {
    return this.http.post<Observable<any>>('http://localhost:3000/auth/login', {
      username: 'cristi',
      password: 'cristi123',
    });
  }

  register(): Observable<any> {
    return this.http.post<Observable<any>>('http://localhost:3000/auth/register', {
      username: 'cristi',
      password: 'cristi123',
      firstName: 'cristi',
      lastName: 'czika',
      email: 'cristi@gmail.com',
    });
  }

  isAuthenticated(): boolean {
    return this.authenticated;
  }
}
