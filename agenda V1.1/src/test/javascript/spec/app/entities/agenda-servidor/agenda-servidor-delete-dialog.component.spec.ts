import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AgendaTestModule } from '../../../test.module';
import { AgendaServidorDeleteDialogComponent } from 'app/entities/agenda-servidor/agenda-servidor-delete-dialog.component';
import { AgendaServidorService } from 'app/entities/agenda-servidor/agenda-servidor.service';

describe('Component Tests', () => {
  describe('AgendaServidor Management Delete Component', () => {
    let comp: AgendaServidorDeleteDialogComponent;
    let fixture: ComponentFixture<AgendaServidorDeleteDialogComponent>;
    let service: AgendaServidorService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AgendaTestModule],
        declarations: [AgendaServidorDeleteDialogComponent]
      })
        .overrideTemplate(AgendaServidorDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgendaServidorDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgendaServidorService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
