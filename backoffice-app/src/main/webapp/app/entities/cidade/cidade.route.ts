import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Cidade } from 'app/shared/model/cidade.model';
import { CidadeService } from './cidade.service';
import { CidadeComponent } from './cidade.component';
import { CidadeDetailComponent } from './cidade-detail.component';
import { CidadeUpdateComponent } from './cidade-update.component';
import { CidadeDeletePopupComponent } from './cidade-delete-dialog.component';
import { ICidade } from 'app/shared/model/cidade.model';

@Injectable({ providedIn: 'root' })
export class CidadeResolve implements Resolve<ICidade> {
  constructor(private service: CidadeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICidade> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Cidade>) => response.ok),
        map((cidade: HttpResponse<Cidade>) => cidade.body)
      );
    }
    return of(new Cidade());
  }
}

export const cidadeRoute: Routes = [
  {
    path: '',
    component: CidadeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'backofficeApp.cidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CidadeDetailComponent,
    resolve: {
      cidade: CidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'backofficeApp.cidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CidadeUpdateComponent,
    resolve: {
      cidade: CidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'backofficeApp.cidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CidadeUpdateComponent,
    resolve: {
      cidade: CidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'backofficeApp.cidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const cidadePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CidadeDeletePopupComponent,
    resolve: {
      cidade: CidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'backofficeApp.cidade.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
