import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UniAgendaTestModule } from '../../../test.module';
import { AgendaReservaSalaDeleteDialogComponent } from 'app/entities/agenda-reserva-sala/agenda-reserva-sala-delete-dialog.component';
import { AgendaReservaSalaService } from 'app/entities/agenda-reserva-sala/agenda-reserva-sala.service';

describe('Component Tests', () => {
  describe('AgendaReservaSala Management Delete Component', () => {
    let comp: AgendaReservaSalaDeleteDialogComponent;
    let fixture: ComponentFixture<AgendaReservaSalaDeleteDialogComponent>;
    let service: AgendaReservaSalaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UniAgendaTestModule],
        declarations: [AgendaReservaSalaDeleteDialogComponent]
      })
        .overrideTemplate(AgendaReservaSalaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgendaReservaSalaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgendaReservaSalaService);
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
