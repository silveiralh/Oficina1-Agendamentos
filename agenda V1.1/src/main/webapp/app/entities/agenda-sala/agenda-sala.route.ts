import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AgendaSala } from 'app/shared/model/agenda-sala.model';
import { AgendaSalaService } from './agenda-sala.service';
import { AgendaSalaComponent } from './agenda-sala.component';
import { AgendaSalaDetailComponent } from './agenda-sala-detail.component';
import { AgendaSalaUpdateComponent } from './agenda-sala-update.component';
import { AgendaSalaDeletePopupComponent } from './agenda-sala-delete-dialog.component';
import { IAgendaSala } from 'app/shared/model/agenda-sala.model';

@Injectable({ providedIn: 'root' })
export class AgendaSalaResolve implements Resolve<IAgendaSala> {
  constructor(private service: AgendaSalaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAgendaSala> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AgendaSala>) => response.ok),
        map((agendaSala: HttpResponse<AgendaSala>) => agendaSala.body)
      );
    }
    return of(new AgendaSala());
  }
}

export const agendaSalaRoute: Routes = [
  {
    path: '',
    component: AgendaSalaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaSalas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AgendaSalaDetailComponent,
    resolve: {
      agendaSala: AgendaSalaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaSalas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AgendaSalaUpdateComponent,
    resolve: {
      agendaSala: AgendaSalaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaSalas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AgendaSalaUpdateComponent,
    resolve: {
      agendaSala: AgendaSalaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaSalas'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const agendaSalaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AgendaSalaDeletePopupComponent,
    resolve: {
      agendaSala: AgendaSalaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaSalas'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
