import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAgendaAluno } from 'app/shared/model/agenda-aluno.model';
import { AccountService } from 'app/core/auth/account.service';
import { AgendaAlunoService } from './agenda-aluno.service';

@Component({
  selector: 'jhi-agenda-aluno',
  templateUrl: './agenda-aluno.component.html'
})
export class AgendaAlunoComponent implements OnInit, OnDestroy {
  agendaAlunos: IAgendaAluno[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected agendaAlunoService: AgendaAlunoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.agendaAlunoService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IAgendaAluno[]>) => res.ok),
          map((res: HttpResponse<IAgendaAluno[]>) => res.body)
        )
        .subscribe((res: IAgendaAluno[]) => (this.agendaAlunos = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.agendaAlunoService
      .query()
      .pipe(
        filter((res: HttpResponse<IAgendaAluno[]>) => res.ok),
        map((res: HttpResponse<IAgendaAluno[]>) => res.body)
      )
      .subscribe(
        (res: IAgendaAluno[]) => {
          this.agendaAlunos = res;
          this.currentSearch = '';
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAgendaAlunos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAgendaAluno) {
    return item.id;
  }

  registerChangeInAgendaAlunos() {
    this.eventSubscriber = this.eventManager.subscribe('agendaAlunoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
