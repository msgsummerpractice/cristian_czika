import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    loadComponent: () =>
      import('./components/gallery/containers/gallery-list-container/gallery-list-container').then(
        (m) => m.GalleryListContainer,
      ),
  },
];
