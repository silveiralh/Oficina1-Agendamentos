import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServidor } from 'app/shared/model/servidor.model';
import { ServidorService } from './servidor.service';

@Component({
  selector: 'jhi-servidor-delete-dialog',
  templateUrl: './servidor-delete-dialog.component.html'
})
export class ServidorDeleteDialogComponent {
  servidor: IServidor;

  constructor(protected servidorService: ServidorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.servidorService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'servidorListModification',
        content: 'Deleted an servidor'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-servidor-delete-popup',
  template: ''
})
export class ServidorDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ servidor }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServidorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.servidor = servidor;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/servidor', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/servidor', { outlets: { popup: null } }]);
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
