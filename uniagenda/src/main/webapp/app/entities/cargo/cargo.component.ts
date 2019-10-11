import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICargo } from 'app/shared/model/cargo.model';
import { AccountService } from 'app/core/auth/account.service';
import { CargoService } from './cargo.service';

@Component({
  selector: 'jhi-cargo',
  templateUrl: './cargo.component.html'
})
export class CargoComponent implements OnInit, OnDestroy {
  cargos: ICargo[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected cargoService: CargoService,
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
      this.cargoService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ICargo[]>) => res.ok),
          map((res: HttpResponse<ICargo[]>) => res.body)
        )
        .subscribe((res: ICargo[]) => (this.cargos = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.cargoService
      .query()
      .pipe(
        filter((res: HttpResponse<ICargo[]>) => res.ok),
        map((res: HttpResponse<ICargo[]>) => res.body)
      )
      .subscribe(
        (res: ICargo[]) => {
          this.cargos = res;
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
    this.registerChangeInCargos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICargo) {
    return item.id;
  }

  registerChangeInCargos() {
    this.eventSubscriber = this.eventManager.subscribe('cargoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
