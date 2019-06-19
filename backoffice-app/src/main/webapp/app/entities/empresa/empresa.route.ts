import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Empresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from './empresa.service';
import { EmpresaComponent } from './empresa.component';
import { EmpresaDetailComponent } from './empresa-detail.component';
import { EmpresaUpdateComponent } from './empresa-update.component';
import { EmpresaDeletePopupComponent } from './empresa-delete-dialog.component';
import { IEmpresa } from 'app/shared/model/empresa.model';

@Injectable({ providedIn: 'root' })
export class EmpresaResolve implements Resolve<IEmpresa> {
  constructor(private service: EmpresaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEmpresa> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Empresa>) => response.ok),
        map((empresa: HttpResponse<Empresa>) => empresa.body)
      );
    }
    return of(new Empresa());
  }
}

export const empresaRoute: Routes = [
  {
    path: '',
    component: EmpresaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'backofficeApp.empresa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EmpresaDetailComponent,
    resolve: {
      empresa: EmpresaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'backofficeApp.empresa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EmpresaUpdateComponent,
    resolve: {
      empresa: EmpresaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'backofficeApp.empresa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EmpresaUpdateComponent,
    resolve: {
      empresa: EmpresaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'backofficeApp.empresa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const empresaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EmpresaDeletePopupComponent,
    resolve: {
      empresa: EmpresaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'backofficeApp.empresa.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
