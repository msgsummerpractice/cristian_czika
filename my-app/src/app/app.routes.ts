import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    loadComponent: () =>
      import('./components/gallery/containers/gallery-container/gallery-container').then(
        (m) => m.GalleryContainer,
      ),
  },
];
