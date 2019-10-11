import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgendaSala } from 'app/shared/model/agenda-sala.model';
import { AgendaSalaService } from './agenda-sala.service';

@Component({
  selector: 'jhi-agenda-sala-delete-dialog',
  templateUrl: './agenda-sala-delete-dialog.component.html'
})
export class AgendaSalaDeleteDialogComponent {
  agendaSala: IAgendaSala;

  constructor(
    protected agendaSalaService: AgendaSalaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.agendaSalaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'agendaSalaListModification',
        content: 'Deleted an agendaSala'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-agenda-sala-delete-popup',
  template: ''
})
export class AgendaSalaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agendaSala }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AgendaSalaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.agendaSala = agendaSala;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/agenda-sala', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/agenda-sala', { outlets: { popup: null } }]);
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
