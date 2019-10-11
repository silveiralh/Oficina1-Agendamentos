import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAgendaReservaSala } from 'app/shared/model/agenda-reserva-sala.model';
import { AccountService } from 'app/core/auth/account.service';
import { AgendaReservaSalaService } from './agenda-reserva-sala.service';

@Component({
  selector: 'jhi-agenda-reserva-sala',
  templateUrl: './agenda-reserva-sala.component.html'
})
export class AgendaReservaSalaComponent implements OnInit, OnDestroy {
  agendaReservaSalas: IAgendaReservaSala[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected agendaReservaSalaService: AgendaReservaSalaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.agendaReservaSalaService
      .query()
      .pipe(
        filter((res: HttpResponse<IAgendaReservaSala[]>) => res.ok),
        map((res: HttpResponse<IAgendaReservaSala[]>) => res.body)
      )
      .subscribe(
        (res: IAgendaReservaSala[]) => {
          this.agendaReservaSalas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAgendaReservaSalas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAgendaReservaSala) {
    return item.id;
  }

  registerChangeInAgendaReservaSalas() {
    this.eventSubscriber = this.eventManager.subscribe('agendaReservaSalaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
