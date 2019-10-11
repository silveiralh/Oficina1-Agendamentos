import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDiasAtendimento } from 'app/shared/model/dias-atendimento.model';
import { DiasAtendimentoService } from './dias-atendimento.service';

@Component({
  selector: 'jhi-dias-atendimento-delete-dialog',
  templateUrl: './dias-atendimento-delete-dialog.component.html'
})
export class DiasAtendimentoDeleteDialogComponent {
  diasAtendimento: IDiasAtendimento;

  constructor(
    protected diasAtendimentoService: DiasAtendimentoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.diasAtendimentoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'diasAtendimentoListModification',
        content: 'Deleted an diasAtendimento'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-dias-atendimento-delete-popup',
  template: ''
})
export class DiasAtendimentoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ diasAtendimento }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DiasAtendimentoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.diasAtendimento = diasAtendimento;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/dias-atendimento', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/dias-atendimento', { outlets: { popup: null } }]);
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
