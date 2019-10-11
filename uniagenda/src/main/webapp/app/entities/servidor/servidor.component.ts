import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IServidor } from 'app/shared/model/servidor.model';
import { AccountService } from 'app/core/auth/account.service';
import { ServidorService } from './servidor.service';

@Component({
  selector: 'jhi-servidor',
  templateUrl: './servidor.component.html'
})
export class ServidorComponent implements OnInit, OnDestroy {
  servidors: IServidor[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected servidorService: ServidorService,
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
      this.servidorService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IServidor[]>) => res.ok),
          map((res: HttpResponse<IServidor[]>) => res.body)
        )
        .subscribe((res: IServidor[]) => (this.servidors = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.servidorService
      .query()
      .pipe(
        filter((res: HttpResponse<IServidor[]>) => res.ok),
        map((res: HttpResponse<IServidor[]>) => res.body)
      )
      .subscribe(
        (res: IServidor[]) => {
          this.servidors = res;
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
    this.registerChangeInServidors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IServidor) {
    return item.id;
  }

  registerChangeInServidors() {
    this.eventSubscriber = this.eventManager.subscribe('servidorListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
