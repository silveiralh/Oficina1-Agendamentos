import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Servidor } from 'app/shared/model/servidor.model';
import { ServidorService } from './servidor.service';
import { ServidorComponent } from './servidor.component';
import { ServidorDetailComponent } from './servidor-detail.component';
import { ServidorUpdateComponent } from './servidor-update.component';
import { ServidorDeletePopupComponent } from './servidor-delete-dialog.component';
import { IServidor } from 'app/shared/model/servidor.model';

@Injectable({ providedIn: 'root' })
export class ServidorResolve implements Resolve<IServidor> {
  constructor(private service: ServidorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServidor> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Servidor>) => response.ok),
        map((servidor: HttpResponse<Servidor>) => servidor.body)
      );
    }
    return of(new Servidor());
  }
}

export const servidorRoute: Routes = [
  {
    path: '',
    component: ServidorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Servidors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServidorDetailComponent,
    resolve: {
      servidor: ServidorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Servidors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServidorUpdateComponent,
    resolve: {
      servidor: ServidorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Servidors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServidorUpdateComponent,
    resolve: {
      servidor: ServidorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Servidors'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const servidorPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServidorDeletePopupComponent,
    resolve: {
      servidor: ServidorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Servidors'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
