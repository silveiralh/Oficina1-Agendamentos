import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgendaAluno } from 'app/shared/model/agenda-aluno.model';
import { AgendaAlunoService } from './agenda-aluno.service';

@Component({
  selector: 'jhi-agenda-aluno-delete-dialog',
  templateUrl: './agenda-aluno-delete-dialog.component.html'
})
export class AgendaAlunoDeleteDialogComponent {
  agendaAluno: IAgendaAluno;

  constructor(
    protected agendaAlunoService: AgendaAlunoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.agendaAlunoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'agendaAlunoListModification',
        content: 'Deleted an agendaAluno'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-agenda-aluno-delete-popup',
  template: ''
})
export class AgendaAlunoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agendaAluno }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AgendaAlunoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.agendaAluno = agendaAluno;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/agenda-aluno', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/agenda-aluno', { outlets: { popup: null } }]);
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
