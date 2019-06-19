import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cidade',
        loadChildren: './cidade/cidade.module#BackofficeCidadeModule'
      },
      {
        path: 'cliente',
        loadChildren: './cliente/cliente.module#BackofficeClienteModule'
      },
      {
        path: 'motoboy',
        loadChildren: './motoboy/motoboy.module#BackofficeMotoboyModule'
      },
      {
        path: 'empresa',
        loadChildren: './empresa/empresa.module#BackofficeEmpresaModule'
      },
      {
        path: 'cidade',
        loadChildren: './cidade/cidade.module#BackofficeCidadeModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BackofficeEntityModule {}
