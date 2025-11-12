import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import SesionResolve from './route/sesion-routing-resolve.service';

const sesionRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/sesion.component').then(m => m.SesionComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/sesion-detail.component').then(m => m.SesionDetailComponent),
    resolve: {
      sesion: SesionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/sesion-update.component').then(m => m.SesionUpdateComponent),
    resolve: {
      sesion: SesionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/sesion-update.component').then(m => m.SesionUpdateComponent),
    resolve: {
      sesion: SesionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default sesionRoute;
