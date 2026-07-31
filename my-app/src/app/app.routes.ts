import { Routes } from '@angular/router';
import { GalleryListContainer } from './components/gallery/containers/gallery-list-container/gallery-list-container';
import { NotFound } from './components/not-found/not-found';
import { authGuard } from './core/guards/auth-guard';
import { confirmExitGuard } from './core/guards/confirm-exit-guard-guard';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'home',
  },
  {
    path: 'home',
    component: GalleryListContainer,
    canActivate: [authGuard],
    canDeactivate: [confirmExitGuard],
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
