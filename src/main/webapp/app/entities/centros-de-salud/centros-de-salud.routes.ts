import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import CentrosDeSaludResolve from './route/centros-de-salud-routing-resolve.service';

const centrosDeSaludRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/centros-de-salud.component').then(m => m.CentrosDeSaludComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/centros-de-salud-detail.component').then(m => m.CentrosDeSaludDetailComponent),
    resolve: {
      centrosDeSalud: CentrosDeSaludResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/centros-de-salud-update.component').then(m => m.CentrosDeSaludUpdateComponent),
    resolve: {
      centrosDeSalud: CentrosDeSaludResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/centros-de-salud-update.component').then(m => m.CentrosDeSaludUpdateComponent),
    resolve: {
      centrosDeSalud: CentrosDeSaludResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default centrosDeSaludRoute;
