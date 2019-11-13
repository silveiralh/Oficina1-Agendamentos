import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AgendaTestModule } from '../../../test.module';
import { CargoDeleteDialogComponent } from 'app/entities/cargo/cargo-delete-dialog.component';
import { CargoService } from 'app/entities/cargo/cargo.service';

describe('Component Tests', () => {
  describe('Cargo Management Delete Component', () => {
    let comp: CargoDeleteDialogComponent;
    let fixture: ComponentFixture<CargoDeleteDialogComponent>;
    let service: CargoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AgendaTestModule],
        declarations: [CargoDeleteDialogComponent]
      })
        .overrideTemplate(CargoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CargoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CargoService);
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
