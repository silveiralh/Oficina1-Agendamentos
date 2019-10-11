import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AgendaReservaSala } from 'app/shared/model/agenda-reserva-sala.model';
import { AgendaReservaSalaService } from './agenda-reserva-sala.service';
import { AgendaReservaSalaComponent } from './agenda-reserva-sala.component';
import { AgendaReservaSalaDetailComponent } from './agenda-reserva-sala-detail.component';
import { AgendaReservaSalaUpdateComponent } from './agenda-reserva-sala-update.component';
import { AgendaReservaSalaDeletePopupComponent } from './agenda-reserva-sala-delete-dialog.component';
import { IAgendaReservaSala } from 'app/shared/model/agenda-reserva-sala.model';

@Injectable({ providedIn: 'root' })
export class AgendaReservaSalaResolve implements Resolve<IAgendaReservaSala> {
  constructor(private service: AgendaReservaSalaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAgendaReservaSala> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AgendaReservaSala>) => response.ok),
        map((agendaReservaSala: HttpResponse<AgendaReservaSala>) => agendaReservaSala.body)
      );
    }
    return of(new AgendaReservaSala());
  }
}

export const agendaReservaSalaRoute: Routes = [
  {
    path: '',
    component: AgendaReservaSalaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaReservaSalas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AgendaReservaSalaDetailComponent,
    resolve: {
      agendaReservaSala: AgendaReservaSalaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaReservaSalas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AgendaReservaSalaUpdateComponent,
    resolve: {
      agendaReservaSala: AgendaReservaSalaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaReservaSalas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AgendaReservaSalaUpdateComponent,
    resolve: {
      agendaReservaSala: AgendaReservaSalaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaReservaSalas'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const agendaReservaSalaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AgendaReservaSalaDeletePopupComponent,
    resolve: {
      agendaReservaSala: AgendaReservaSalaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaReservaSalas'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
