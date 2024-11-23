import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'evaluacionSumativaApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'paciente',
    data: { pageTitle: 'evaluacionSumativaApp.paciente.home.title' },
    loadChildren: () => import('./paciente/paciente.routes'),
  },
  {
    path: 'medicos',
    data: { pageTitle: 'evaluacionSumativaApp.medicos.home.title' },
    loadChildren: () => import('./medicos/medicos.routes'),
  },
  {
    path: 'centros-de-salud',
    data: { pageTitle: 'evaluacionSumativaApp.centrosDeSalud.home.title' },
    loadChildren: () => import('./centros-de-salud/centros-de-salud.routes'),
  },

  {
    path: 'agendamiento',
    data: { pageTitle: 'evaluacionSumativaApp.agendamiento.home.title' },
    loadChildren: () => import('./agendamiento/agendamiento.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
