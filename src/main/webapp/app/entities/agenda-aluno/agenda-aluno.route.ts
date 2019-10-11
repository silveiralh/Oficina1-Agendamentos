import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AgendaAluno } from 'app/shared/model/agenda-aluno.model';
import { AgendaAlunoService } from './agenda-aluno.service';
import { AgendaAlunoComponent } from './agenda-aluno.component';
import { AgendaAlunoDetailComponent } from './agenda-aluno-detail.component';
import { AgendaAlunoUpdateComponent } from './agenda-aluno-update.component';
import { AgendaAlunoDeletePopupComponent } from './agenda-aluno-delete-dialog.component';
import { IAgendaAluno } from 'app/shared/model/agenda-aluno.model';

@Injectable({ providedIn: 'root' })
export class AgendaAlunoResolve implements Resolve<IAgendaAluno> {
  constructor(private service: AgendaAlunoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAgendaAluno> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AgendaAluno>) => response.ok),
        map((agendaAluno: HttpResponse<AgendaAluno>) => agendaAluno.body)
      );
    }
    return of(new AgendaAluno());
  }
}

export const agendaAlunoRoute: Routes = [
  {
    path: '',
    component: AgendaAlunoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaAlunos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AgendaAlunoDetailComponent,
    resolve: {
      agendaAluno: AgendaAlunoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaAlunos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AgendaAlunoUpdateComponent,
    resolve: {
      agendaAluno: AgendaAlunoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaAlunos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AgendaAlunoUpdateComponent,
    resolve: {
      agendaAluno: AgendaAlunoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaAlunos'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const agendaAlunoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AgendaAlunoDeletePopupComponent,
    resolve: {
      agendaAluno: AgendaAlunoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AgendaAlunos'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
