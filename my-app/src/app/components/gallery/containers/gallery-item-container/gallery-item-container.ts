import { Component, input } from '@angular/core';
import { Dog } from '../../../../core/model/dog.model';
import { GalleryItemView } from '../../views/gallery-item-view/gallery-item-view';

@Component({
  selector: 'app-gallery-item-container',
  imports: [GalleryItemView],
  templateUrl: './gallery-item-container.html',
})
export class GalleryItemContainer {
  dog = input.required<Dog>();
}
