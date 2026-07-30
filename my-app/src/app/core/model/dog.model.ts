import { Observable } from 'rxjs';

export interface Dog {
  name: string;
  description: string;
  image$: Observable<string>;
}
