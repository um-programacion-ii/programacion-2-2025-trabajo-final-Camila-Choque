import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import IntegrantesResolve from './route/integrantes-routing-resolve.service';

const integrantesRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/integrantes.component').then(m => m.IntegrantesComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/integrantes-detail.component').then(m => m.IntegrantesDetailComponent),
    resolve: {
      integrantes: IntegrantesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/integrantes-update.component').then(m => m.IntegrantesUpdateComponent),
    resolve: {
      integrantes: IntegrantesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/integrantes-update.component').then(m => m.IntegrantesUpdateComponent),
    resolve: {
      integrantes: IntegrantesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default integrantesRoute;
