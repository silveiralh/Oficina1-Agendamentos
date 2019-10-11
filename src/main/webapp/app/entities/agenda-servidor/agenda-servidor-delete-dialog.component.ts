import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgendaServidor } from 'app/shared/model/agenda-servidor.model';
import { AgendaServidorService } from './agenda-servidor.service';

@Component({
  selector: 'jhi-agenda-servidor-delete-dialog',
  templateUrl: './agenda-servidor-delete-dialog.component.html'
})
export class AgendaServidorDeleteDialogComponent {
  agendaServidor: IAgendaServidor;

  constructor(
    protected agendaServidorService: AgendaServidorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.agendaServidorService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'agendaServidorListModification',
        content: 'Deleted an agendaServidor'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-agenda-servidor-delete-popup',
  template: ''
})
export class AgendaServidorDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agendaServidor }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AgendaServidorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.agendaServidor = agendaServidor;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/agenda-servidor', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/agenda-servidor', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
