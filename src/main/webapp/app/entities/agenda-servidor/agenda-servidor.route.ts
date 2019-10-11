import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AgendaServidor } from 'app/shared/model/agenda-servidor.model';
import { AgendaServidorService } from './agenda-servidor.service';
import { AgendaServidorComponent } from './agenda-servidor.component';
import { AgendaServidorDetailComponent } from './agenda-servidor-detail.component';
import { AgendaServidorUpdateComponent } from './agenda-servidor-update.component';
import { AgendaServidorDeletePopupComponent } from './agenda-servidor-delete-dialog.component';
import { IAgendaServidor } from 'app/shared/model/agenda-servidor.model';

@Injectable({ providedIn: 'root' })
export class AgendaServidorResolve implements Resolve<IAgendaServidor> {
  constructor(private service: AgendaServidorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAgendaServidor> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AgendaServidor>) => response.ok),
        map((agendaServidor: HttpResponse<AgendaServidor>) => agendaServidor.body)
      );
    }
    return of(new AgendaServidor());
  }
}

export const agendaServidorRoute: Routes = [
  {
    path: '',
    component: AgendaServidorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaServidors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AgendaServidorDetailComponent,
    resolve: {
      agendaServidor: AgendaServidorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaServidors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AgendaServidorUpdateComponent,
    resolve: {
      agendaServidor: AgendaServidorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaServidors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AgendaServidorUpdateComponent,
    resolve: {
      agendaServidor: AgendaServidorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaServidors'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const agendaServidorPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AgendaServidorDeletePopupComponent,
    resolve: {
      agendaServidor: AgendaServidorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaServidors'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
