import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAluno } from 'app/shared/model/aluno.model';
import { AccountService } from 'app/core/auth/account.service';
import { AlunoService } from './aluno.service';

@Component({
  selector: 'jhi-aluno',
  templateUrl: './aluno.component.html'
})
export class AlunoComponent implements OnInit, OnDestroy {
  alunos: IAluno[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected alunoService: AlunoService,
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
      this.alunoService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IAluno[]>) => res.ok),
          map((res: HttpResponse<IAluno[]>) => res.body)
        )
        .subscribe((res: IAluno[]) => (this.alunos = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.alunoService
      .query()
      .pipe(
        filter((res: HttpResponse<IAluno[]>) => res.ok),
        map((res: HttpResponse<IAluno[]>) => res.body)
      )
      .subscribe(
        (res: IAluno[]) => {
          this.alunos = res;
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
    this.registerChangeInAlunos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAluno) {
    return item.id;
  }

  registerChangeInAlunos() {
    this.eventSubscriber = this.eventManager.subscribe('alunoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
