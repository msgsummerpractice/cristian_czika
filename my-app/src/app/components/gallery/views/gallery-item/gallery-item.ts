import { AsyncPipe } from '@angular/common';
import { Component, input } from '@angular/core';

export interface DogData {
  name: string;
  description: string;
  imageUrl: string;
}

@Component({
  selector: 'app-gallery-item',
  imports: [],
  templateUrl: './gallery-item.html',
})
export class GalleryItem {
  dog = input.required<DogData>();
}
