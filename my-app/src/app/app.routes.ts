import { Routes } from '@angular/router';
import { GalleryListContainer } from './components/gallery/containers/gallery-list-container/gallery-list-container';
import { NotFound } from './components/not-found/not-found';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'home',
  },
  {
    path: 'home',
    component: GalleryListContainer,
  },
  {
    path: 'login',
    loadComponent: () =>
      import('./components/login/containers/login-container/login-container').then(
        (m) => m.LoginContainer,
      ),
  },
  {
    path: '**',
    component: NotFound,
  },
];
