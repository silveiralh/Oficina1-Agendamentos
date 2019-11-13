import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
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

  constructor(
    protected alunoService: AlunoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.alunoService
      .query()
      .pipe(
        filter((res: HttpResponse<IAluno[]>) => res.ok),
        map((res: HttpResponse<IAluno[]>) => res.body)
      )
      .subscribe(
        (res: IAluno[]) => {
          this.alunos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
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
