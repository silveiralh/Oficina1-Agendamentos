import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAgendaAtendimentoServidor } from 'app/shared/model/agenda-atendimento-servidor.model';
import { AccountService } from 'app/core/auth/account.service';
import { AgendaAtendimentoServidorService } from './agenda-atendimento-servidor.service';

@Component({
  selector: 'jhi-agenda-atendimento-servidor',
  templateUrl: './agenda-atendimento-servidor.component.html'
})
export class AgendaAtendimentoServidorComponent implements OnInit, OnDestroy {
  agendaAtendimentoServidors: IAgendaAtendimentoServidor[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected agendaAtendimentoServidorService: AgendaAtendimentoServidorService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.agendaAtendimentoServidorService
      .query()
      .pipe(
        filter((res: HttpResponse<IAgendaAtendimentoServidor[]>) => res.ok),
        map((res: HttpResponse<IAgendaAtendimentoServidor[]>) => res.body)
      )
      .subscribe(
        (res: IAgendaAtendimentoServidor[]) => {
          this.agendaAtendimentoServidors = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAgendaAtendimentoServidors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAgendaAtendimentoServidor) {
    return item.id;
  }

  registerChangeInAgendaAtendimentoServidors() {
    this.eventSubscriber = this.eventManager.subscribe('agendaAtendimentoServidorListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
