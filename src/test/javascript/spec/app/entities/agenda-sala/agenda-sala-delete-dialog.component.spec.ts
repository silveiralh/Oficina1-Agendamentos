import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UniagendaTestModule } from '../../../test.module';
import { AgendaSalaDeleteDialogComponent } from 'app/entities/agenda-sala/agenda-sala-delete-dialog.component';
import { AgendaSalaService } from 'app/entities/agenda-sala/agenda-sala.service';

describe('Component Tests', () => {
  describe('AgendaSala Management Delete Component', () => {
    let comp: AgendaSalaDeleteDialogComponent;
    let fixture: ComponentFixture<AgendaSalaDeleteDialogComponent>;
    let service: AgendaSalaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UniagendaTestModule],
        declarations: [AgendaSalaDeleteDialogComponent]
      })
        .overrideTemplate(AgendaSalaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgendaSalaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgendaSalaService);
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
