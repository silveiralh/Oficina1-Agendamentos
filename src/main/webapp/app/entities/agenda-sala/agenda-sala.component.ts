import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAgendaSala } from 'app/shared/model/agenda-sala.model';
import { AccountService } from 'app/core/auth/account.service';
import { AgendaSalaService } from './agenda-sala.service';

@Component({
  selector: 'jhi-agenda-sala',
  templateUrl: './agenda-sala.component.html'
})
export class AgendaSalaComponent implements OnInit, OnDestroy {
  agendaSalas: IAgendaSala[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected agendaSalaService: AgendaSalaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.agendaSalaService
      .query()
      .pipe(
        filter((res: HttpResponse<IAgendaSala[]>) => res.ok),
        map((res: HttpResponse<IAgendaSala[]>) => res.body)
      )
      .subscribe(
        (res: IAgendaSala[]) => {
          this.agendaSalas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAgendaSalas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAgendaSala) {
    return item.id;
  }

  registerChangeInAgendaSalas() {
    this.eventSubscriber = this.eventManager.subscribe('agendaSalaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
