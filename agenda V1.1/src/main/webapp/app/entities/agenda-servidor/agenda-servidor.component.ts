import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAgendaServidor } from 'app/shared/model/agenda-servidor.model';
import { AccountService } from 'app/core/auth/account.service';
import { AgendaServidorService } from './agenda-servidor.service';

@Component({
  selector: 'jhi-agenda-servidor',
  templateUrl: './agenda-servidor.component.html'
})
export class AgendaServidorComponent implements OnInit, OnDestroy {
  agendaServidors: IAgendaServidor[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected agendaServidorService: AgendaServidorService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.agendaServidorService
      .query()
      .pipe(
        filter((res: HttpResponse<IAgendaServidor[]>) => res.ok),
        map((res: HttpResponse<IAgendaServidor[]>) => res.body)
      )
      .subscribe(
        (res: IAgendaServidor[]) => {
          this.agendaServidors = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAgendaServidors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAgendaServidor) {
    return item.id;
  }

  registerChangeInAgendaServidors() {
    this.eventSubscriber = this.eventManager.subscribe('agendaServidorListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
