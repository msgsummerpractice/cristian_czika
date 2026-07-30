import { Component, inject } from '@angular/core';
import { DogService } from '../../core/dogservice';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-gallery',
  imports: [AsyncPipe],
  templateUrl: './gallery.html',
})
export class Gallery {
  private dogService = inject(DogService);

  dogs = [
    {
      name: 'Golden Retriever',
      description:
        'Originally bred as gundogs for retrieving waterfowl, Golden Retrievers are famous for their gentle "soft mouths" and eager-to-please attitudes.',
      image$: this.dogService.getRandomDogImage(),
    },
    {
      name: 'Border Collie',
      description:
        'Widely considered the most intelligent of all domestic dogs, Border Collies possess an intense herding instinct. They use a famous "herding eye"—an intense stare—to control livestock.',
      image$: this.dogService.getRandomDogImage(),
    },
    {
      name: 'French Bulldog',
      description:
        'Lovingly nicknamed the "Frenchie," the French Bulldog was bred in Paris in the mid-19th century to be a miniature companion dog. They are incredibly quiet, rarely bark, and thrive on human companionship.',
      image$: this.dogService.getRandomDogImage(),
    },
  ];
}
