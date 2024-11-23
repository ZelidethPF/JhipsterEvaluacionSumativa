import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import AgendamientoResolve from './route/agendamiento-routing-resolve.service';

const agendamientoRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/agendamiento.component').then(m => m.AgendamientoComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/agendamiento-detail.component').then(m => m.AgendamientoDetailComponent),
    resolve: {
      agendamiento: AgendamientoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/agendamiento-update.component').then(m => m.AgendamientoUpdateComponent),
    resolve: {
      agendamiento: AgendamientoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/agendamiento-update.component').then(m => m.AgendamientoUpdateComponent),
    resolve: {
      agendamiento: AgendamientoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default agendamientoRoute;
