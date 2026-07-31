import { Component, input } from '@angular/core';
import { Dog } from '../../../../core/model/dog.model';
import { GalleryItemView } from '../../views/gallery-item-view/gallery-item-view';

@Component({
  selector: 'app-gallery-item-container',
  imports: [GalleryItemView],
  template: '<app-gallery-item-view [dog]="dog()"/>',
})
export class GalleryItemContainer {
  dog = input.required<Dog>();
}
