import { TestBed } from '@angular/core/testing';

import { Dogservice } from './dogservice';

describe('Dogservice', () => {
  let service: Dogservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Dogservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
