import { Component, inject } from '@angular/core';
import { DogService } from '../../../../core/services/dogservice';
import { Dog } from '../../../../core/model/dog.model';
import { GalleryListView } from '../../views/gallery-list-view/gallery-list-view';
import { toSignal } from '@angular/core/rxjs-interop';
import { forkJoin, map } from 'rxjs';

@Component({
  selector: 'app-gallery-list-container',
  imports: [GalleryListView],
  templateUrl: './gallery-list-container.html',
})
export class GalleryListContainer {
  private dogService = inject(DogService);

  private dogData$ = forkJoin([
    this.dogService.getRandomDogImage(),
    this.dogService.getRandomDogImage(),
    this.dogService.getRandomDogImage(),
  ]).pipe(
    map(([goldenUrl, collieUrl, frenchieUrl]): Dog[] => [
      {
        name: 'Golden Retriever',
        description:
          'Originally bred as gundogs for retrieving waterfowl, Golden Retrievers are famous for their gentle "soft mouths" and eager-to-please attitudes.',
        imageUrl: goldenUrl,
      },
      {
        name: 'Border Collie',
        description:
          'Widely considered the most intelligent of all domestic dogs, Border Collies possess an intense herding instinct. They use a famous "herding eye"—an intense stare—to control livestock.',
        imageUrl: collieUrl,
      },
      {
        name: 'French Bulldog',
        description:
          'Lovingly nicknamed the "Frenchie," the French Bulldog was bred in Paris in the mid-19th century to be a miniature companion dog. They are incredibly quiet, rarely bark, and thrive on human companionship.',
        imageUrl: frenchieUrl,
      },
    ]),
  );

  dogs = toSignal(this.dogData$, { initialValue: [] });
}
