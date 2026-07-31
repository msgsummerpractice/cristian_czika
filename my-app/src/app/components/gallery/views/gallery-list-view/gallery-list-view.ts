import { Component, input } from '@angular/core';
import { Dog } from '../../../../core/model/dog.model';
import { GalleryItemContainer } from '../../containers/gallery-item-container/gallery-item-container';
import { UsernameFormatPipe } from '../../../../core/pipes/username-format-pipe';

@Component({
  selector: 'app-gallery-list-view',
  imports: [GalleryItemContainer, UsernameFormatPipe],
  templateUrl: './gallery-list-view.html',
})
export class GalleryListView {
  dogs = input.required<Dog[]>();
}
