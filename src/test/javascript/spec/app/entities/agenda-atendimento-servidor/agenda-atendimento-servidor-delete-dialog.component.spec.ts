import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UniagendaTestModule } from '../../../test.module';
import { AgendaAtendimentoServidorDeleteDialogComponent } from 'app/entities/agenda-atendimento-servidor/agenda-atendimento-servidor-delete-dialog.component';
import { AgendaAtendimentoServidorService } from 'app/entities/agenda-atendimento-servidor/agenda-atendimento-servidor.service';

describe('Component Tests', () => {
  describe('AgendaAtendimentoServidor Management Delete Component', () => {
    let comp: AgendaAtendimentoServidorDeleteDialogComponent;
    let fixture: ComponentFixture<AgendaAtendimentoServidorDeleteDialogComponent>;
    let service: AgendaAtendimentoServidorService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UniagendaTestModule],
        declarations: [AgendaAtendimentoServidorDeleteDialogComponent]
      })
        .overrideTemplate(AgendaAtendimentoServidorDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgendaAtendimentoServidorDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgendaAtendimentoServidorService);
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
