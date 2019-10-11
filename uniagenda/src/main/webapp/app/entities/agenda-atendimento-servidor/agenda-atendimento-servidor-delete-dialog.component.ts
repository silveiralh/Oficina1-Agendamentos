import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgendaAtendimentoServidor } from 'app/shared/model/agenda-atendimento-servidor.model';
import { AgendaAtendimentoServidorService } from './agenda-atendimento-servidor.service';

@Component({
  selector: 'jhi-agenda-atendimento-servidor-delete-dialog',
  templateUrl: './agenda-atendimento-servidor-delete-dialog.component.html'
})
export class AgendaAtendimentoServidorDeleteDialogComponent {
  agendaAtendimentoServidor: IAgendaAtendimentoServidor;

  constructor(
    protected agendaAtendimentoServidorService: AgendaAtendimentoServidorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.agendaAtendimentoServidorService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'agendaAtendimentoServidorListModification',
        content: 'Deleted an agendaAtendimentoServidor'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-agenda-atendimento-servidor-delete-popup',
  template: ''
})
export class AgendaAtendimentoServidorDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agendaAtendimentoServidor }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AgendaAtendimentoServidorDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.agendaAtendimentoServidor = agendaAtendimentoServidor;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/agenda-atendimento-servidor', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/agenda-atendimento-servidor', { outlets: { popup: null } }]);
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
