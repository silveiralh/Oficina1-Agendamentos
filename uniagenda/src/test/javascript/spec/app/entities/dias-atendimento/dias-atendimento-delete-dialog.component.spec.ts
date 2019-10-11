import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UniAgendaTestModule } from '../../../test.module';
import { DiasAtendimentoDeleteDialogComponent } from 'app/entities/dias-atendimento/dias-atendimento-delete-dialog.component';
import { DiasAtendimentoService } from 'app/entities/dias-atendimento/dias-atendimento.service';

describe('Component Tests', () => {
  describe('DiasAtendimento Management Delete Component', () => {
    let comp: DiasAtendimentoDeleteDialogComponent;
    let fixture: ComponentFixture<DiasAtendimentoDeleteDialogComponent>;
    let service: DiasAtendimentoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UniAgendaTestModule],
        declarations: [DiasAtendimentoDeleteDialogComponent]
      })
        .overrideTemplate(DiasAtendimentoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DiasAtendimentoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DiasAtendimentoService);
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
