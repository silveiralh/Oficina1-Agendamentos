import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DiasAtendimento } from 'app/shared/model/dias-atendimento.model';
import { DiasAtendimentoService } from './dias-atendimento.service';
import { DiasAtendimentoComponent } from './dias-atendimento.component';
import { DiasAtendimentoDetailComponent } from './dias-atendimento-detail.component';
import { DiasAtendimentoUpdateComponent } from './dias-atendimento-update.component';
import { DiasAtendimentoDeletePopupComponent } from './dias-atendimento-delete-dialog.component';
import { IDiasAtendimento } from 'app/shared/model/dias-atendimento.model';

@Injectable({ providedIn: 'root' })
export class DiasAtendimentoResolve implements Resolve<IDiasAtendimento> {
  constructor(private service: DiasAtendimentoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDiasAtendimento> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DiasAtendimento>) => response.ok),
        map((diasAtendimento: HttpResponse<DiasAtendimento>) => diasAtendimento.body)
      );
    }
    return of(new DiasAtendimento());
  }
}

export const diasAtendimentoRoute: Routes = [
  {
    path: '',
    component: DiasAtendimentoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'uniAgendaApp.diasAtendimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DiasAtendimentoDetailComponent,
    resolve: {
      diasAtendimento: DiasAtendimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'uniAgendaApp.diasAtendimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DiasAtendimentoUpdateComponent,
    resolve: {
      diasAtendimento: DiasAtendimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'uniAgendaApp.diasAtendimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DiasAtendimentoUpdateComponent,
    resolve: {
      diasAtendimento: DiasAtendimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'uniAgendaApp.diasAtendimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const diasAtendimentoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DiasAtendimentoDeletePopupComponent,
    resolve: {
      diasAtendimento: DiasAtendimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'uniAgendaApp.diasAtendimento.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
