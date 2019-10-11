import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgendaReservaSala } from 'app/shared/model/agenda-reserva-sala.model';
import { AgendaReservaSalaService } from './agenda-reserva-sala.service';

@Component({
  selector: 'jhi-agenda-reserva-sala-delete-dialog',
  templateUrl: './agenda-reserva-sala-delete-dialog.component.html'
})
export class AgendaReservaSalaDeleteDialogComponent {
  agendaReservaSala: IAgendaReservaSala;

  constructor(
    protected agendaReservaSalaService: AgendaReservaSalaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.agendaReservaSalaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'agendaReservaSalaListModification',
        content: 'Deleted an agendaReservaSala'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-agenda-reserva-sala-delete-popup',
  template: ''
})
export class AgendaReservaSalaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agendaReservaSala }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AgendaReservaSalaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.agendaReservaSala = agendaReservaSala;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/agenda-reserva-sala', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/agenda-reserva-sala', { outlets: { popup: null } }]);
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
