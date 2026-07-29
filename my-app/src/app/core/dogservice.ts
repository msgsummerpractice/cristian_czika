import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { map, Observable } from 'rxjs';

@Service()
export class Dogservice {
  private http = inject(HttpClient);
  private apiUrl = 'https://dog.ceo/api/breeds/image/random';

  getRandomDogImage(): Observable<string> {
    return this.http
      .get<{ message: string; status: string }>(this.apiUrl)
      .pipe(map((response) => response.message));
  }
}
