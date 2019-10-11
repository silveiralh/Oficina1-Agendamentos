import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AgendaAtendimentoServidor } from 'app/shared/model/agenda-atendimento-servidor.model';
import { AgendaAtendimentoServidorService } from './agenda-atendimento-servidor.service';
import { AgendaAtendimentoServidorComponent } from './agenda-atendimento-servidor.component';
import { AgendaAtendimentoServidorDetailComponent } from './agenda-atendimento-servidor-detail.component';
import { AgendaAtendimentoServidorUpdateComponent } from './agenda-atendimento-servidor-update.component';
import { AgendaAtendimentoServidorDeletePopupComponent } from './agenda-atendimento-servidor-delete-dialog.component';
import { IAgendaAtendimentoServidor } from 'app/shared/model/agenda-atendimento-servidor.model';

@Injectable({ providedIn: 'root' })
export class AgendaAtendimentoServidorResolve implements Resolve<IAgendaAtendimentoServidor> {
  constructor(private service: AgendaAtendimentoServidorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAgendaAtendimentoServidor> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AgendaAtendimentoServidor>) => response.ok),
        map((agendaAtendimentoServidor: HttpResponse<AgendaAtendimentoServidor>) => agendaAtendimentoServidor.body)
      );
    }
    return of(new AgendaAtendimentoServidor());
  }
}

export const agendaAtendimentoServidorRoute: Routes = [
  {
    path: '',
    component: AgendaAtendimentoServidorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaAtendimentoServidors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AgendaAtendimentoServidorDetailComponent,
    resolve: {
      agendaAtendimentoServidor: AgendaAtendimentoServidorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaAtendimentoServidors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AgendaAtendimentoServidorUpdateComponent,
    resolve: {
      agendaAtendimentoServidor: AgendaAtendimentoServidorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaAtendimentoServidors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AgendaAtendimentoServidorUpdateComponent,
    resolve: {
      agendaAtendimentoServidor: AgendaAtendimentoServidorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaAtendimentoServidors'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const agendaAtendimentoServidorPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AgendaAtendimentoServidorDeletePopupComponent,
    resolve: {
      agendaAtendimentoServidor: AgendaAtendimentoServidorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaAtendimentoServidors'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
