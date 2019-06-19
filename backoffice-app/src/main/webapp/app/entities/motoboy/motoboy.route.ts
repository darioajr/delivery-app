import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Motoboy } from 'app/shared/model/motoboy.model';
import { MotoboyService } from './motoboy.service';
import { MotoboyComponent } from './motoboy.component';
import { MotoboyDetailComponent } from './motoboy-detail.component';
import { MotoboyUpdateComponent } from './motoboy-update.component';
import { MotoboyDeletePopupComponent } from './motoboy-delete-dialog.component';
import { IMotoboy } from 'app/shared/model/motoboy.model';

@Injectable({ providedIn: 'root' })
export class MotoboyResolve implements Resolve<IMotoboy> {
  constructor(private service: MotoboyService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMotoboy> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Motoboy>) => response.ok),
        map((motoboy: HttpResponse<Motoboy>) => motoboy.body)
      );
    }
    return of(new Motoboy());
  }
}

export const motoboyRoute: Routes = [
  {
    path: '',
    component: MotoboyComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'backofficeApp.motoboy.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MotoboyDetailComponent,
    resolve: {
      motoboy: MotoboyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'backofficeApp.motoboy.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MotoboyUpdateComponent,
    resolve: {
      motoboy: MotoboyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'backofficeApp.motoboy.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MotoboyUpdateComponent,
    resolve: {
      motoboy: MotoboyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'backofficeApp.motoboy.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const motoboyPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MotoboyDeletePopupComponent,
    resolve: {
      motoboy: MotoboyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'backofficeApp.motoboy.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
