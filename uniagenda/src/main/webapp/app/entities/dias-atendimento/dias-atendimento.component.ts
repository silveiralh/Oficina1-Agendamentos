import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
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
  currentSearch: string;

  constructor(
    protected diasAtendimentoService: DiasAtendimentoService,
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
      this.diasAtendimentoService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IDiasAtendimento[]>) => res.ok),
          map((res: HttpResponse<IDiasAtendimento[]>) => res.body)
        )
        .subscribe((res: IDiasAtendimento[]) => (this.diasAtendimentos = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.diasAtendimentoService
      .query()
      .pipe(
        filter((res: HttpResponse<IDiasAtendimento[]>) => res.ok),
        map((res: HttpResponse<IDiasAtendimento[]>) => res.body)
      )
      .subscribe(
        (res: IDiasAtendimento[]) => {
          this.diasAtendimentos = res;
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
