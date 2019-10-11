import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDiasAtendimento } from 'app/shared/model/dias-atendimento.model';
import { AccountService } from 'app/core/auth/account.service';
import { DiasAtendimentoService } from './dias-atendimento.service';

@Component({
  selector: 'jhi-dias-atendimento',
  templateUrl: './dias-atendimento.component.html'
})
export class DiasAtendimentoComponent implements OnInit, OnDestroy {
  diasAtendimentos: IDiasAtendimento[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected diasAtendimentoService: DiasAtendimentoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.diasAtendimentoService
      .query()
      .pipe(
        filter((res: HttpResponse<IDiasAtendimento[]>) => res.ok),
        map((res: HttpResponse<IDiasAtendimento[]>) => res.body)
      )
      .subscribe(
        (res: IDiasAtendimento[]) => {
          this.diasAtendimentos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDiasAtendimentos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDiasAtendimento) {
    return item.id;
  }

  registerChangeInDiasAtendimentos() {
    this.eventSubscriber = this.eventManager.subscribe('diasAtendimentoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
