import { Component, input } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { Dog } from '../../../../core/model/dog.model';

@Component({
  selector: 'app-gallery-item-view',
  imports: [AsyncPipe],
  templateUrl: './gallery-item-view.html',
})
export class GalleryItemView {
  dog = input.required<Dog>();
}
