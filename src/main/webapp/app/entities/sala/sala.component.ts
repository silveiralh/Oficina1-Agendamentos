import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISala } from 'app/shared/model/sala.model';
import { AccountService } from 'app/core/auth/account.service';
import { SalaService } from './sala.service';

@Component({
  selector: 'jhi-sala',
  templateUrl: './sala.component.html'
})
export class SalaComponent implements OnInit, OnDestroy {
  salas: ISala[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected salaService: SalaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.salaService
      .query()
      .pipe(
        filter((res: HttpResponse<ISala[]>) => res.ok),
        map((res: HttpResponse<ISala[]>) => res.body)
      )
      .subscribe(
        (res: ISala[]) => {
          this.salas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSalas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISala) {
    return item.id;
  }

  registerChangeInSalas() {
    this.eventSubscriber = this.eventManager.subscribe('salaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
