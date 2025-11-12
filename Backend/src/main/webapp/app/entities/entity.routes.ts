import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'backendApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'usuario',
    data: { pageTitle: 'backendApp.usuario.home.title' },
    loadChildren: () => import('./usuario/usuario.routes'),
  },
  {
    path: 'evento',
    data: { pageTitle: 'backendApp.evento.home.title' },
    loadChildren: () => import('./evento/evento.routes'),
  },
  {
    path: 'integrantes',
    data: { pageTitle: 'backendApp.integrantes.home.title' },
    loadChildren: () => import('./integrantes/integrantes.routes'),
  },
  {
    path: 'venta',
    data: { pageTitle: 'backendApp.venta.home.title' },
    loadChildren: () => import('./venta/venta.routes'),
  },
  {
    path: 'asientos',
    data: { pageTitle: 'backendApp.asientos.home.title' },
    loadChildren: () => import('./asientos/asientos.routes'),
  },
  {
    path: 'sesion',
    data: { pageTitle: 'backendApp.sesion.home.title' },
    loadChildren: () => import('./sesion/sesion.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
