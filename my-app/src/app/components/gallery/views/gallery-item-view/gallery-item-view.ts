import { Component, input } from '@angular/core';
import { Dog } from '../../../../core/model/dog.model';

@Component({
  selector: 'app-gallery-item-view',
  imports: [],
  templateUrl: './gallery-item-view.html',
})
export class GalleryItemView {
  dog = input.required<Dog>();
}
