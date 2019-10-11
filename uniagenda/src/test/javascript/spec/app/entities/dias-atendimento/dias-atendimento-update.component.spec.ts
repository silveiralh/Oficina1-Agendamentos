import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { UniAgendaTestModule } from '../../../test.module';
import { DiasAtendimentoUpdateComponent } from 'app/entities/dias-atendimento/dias-atendimento-update.component';
import { DiasAtendimentoService } from 'app/entities/dias-atendimento/dias-atendimento.service';
import { DiasAtendimento } from 'app/shared/model/dias-atendimento.model';

describe('Component Tests', () => {
  describe('DiasAtendimento Management Update Component', () => {
    let comp: DiasAtendimentoUpdateComponent;
    let fixture: ComponentFixture<DiasAtendimentoUpdateComponent>;
    let service: DiasAtendimentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UniAgendaTestModule],
        declarations: [DiasAtendimentoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DiasAtendimentoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DiasAtendimentoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DiasAtendimentoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DiasAtendimento(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DiasAtendimento();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
