import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { map, Observable } from 'rxjs';
import { DogApiResponse } from '../dto/dogapiresponse';

@Service()
export class DogService {
  private http = inject(HttpClient);
  private readonly apiUrl = 'https://dog.ceo/api/breeds/image/random';

  getRandomDogImage(): Observable<string> {
    return this.http.get<DogApiResponse>(this.apiUrl).pipe(map((response) => response.message));
  }
}
