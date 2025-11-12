import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import AsientosResolve from './route/asientos-routing-resolve.service';

const asientosRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/asientos.component').then(m => m.AsientosComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/asientos-detail.component').then(m => m.AsientosDetailComponent),
    resolve: {
      asientos: AsientosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/asientos-update.component').then(m => m.AsientosUpdateComponent),
    resolve: {
      asientos: AsientosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/asientos-update.component').then(m => m.AsientosUpdateComponent),
    resolve: {
      asientos: AsientosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default asientosRoute;
