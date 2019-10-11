import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UniagendaTestModule } from '../../../test.module';
import { AgendaAlunoDeleteDialogComponent } from 'app/entities/agenda-aluno/agenda-aluno-delete-dialog.component';
import { AgendaAlunoService } from 'app/entities/agenda-aluno/agenda-aluno.service';

describe('Component Tests', () => {
  describe('AgendaAluno Management Delete Component', () => {
    let comp: AgendaAlunoDeleteDialogComponent;
    let fixture: ComponentFixture<AgendaAlunoDeleteDialogComponent>;
    let service: AgendaAlunoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UniagendaTestModule],
        declarations: [AgendaAlunoDeleteDialogComponent]
      })
        .overrideTemplate(AgendaAlunoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgendaAlunoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgendaAlunoService);
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
