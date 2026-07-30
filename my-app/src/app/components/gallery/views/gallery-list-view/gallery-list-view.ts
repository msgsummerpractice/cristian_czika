import { Component, input } from '@angular/core';
import { Dog } from '../../../../core/model/dog.model';
import { GalleryItemContainer } from '../../containers/gallery-item-container/gallery-item-container';

@Component({
  selector: 'app-gallery-list-view',
  imports: [GalleryItemContainer],
  templateUrl: './gallery-list-view.html',
})
export class GalleryListView {
  dogs = input.required<Dog[]>();
}
