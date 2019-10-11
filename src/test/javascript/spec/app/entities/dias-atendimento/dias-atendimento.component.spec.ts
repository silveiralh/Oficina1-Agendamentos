import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UniagendaTestModule } from '../../../test.module';
import { DiasAtendimentoComponent } from 'app/entities/dias-atendimento/dias-atendimento.component';
import { DiasAtendimentoService } from 'app/entities/dias-atendimento/dias-atendimento.service';
import { DiasAtendimento } from 'app/shared/model/dias-atendimento.model';

describe('Component Tests', () => {
  describe('DiasAtendimento Management Component', () => {
    let comp: DiasAtendimentoComponent;
    let fixture: ComponentFixture<DiasAtendimentoComponent>;
    let service: DiasAtendimentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UniagendaTestModule],
        declarations: [DiasAtendimentoComponent],
        providers: []
      })
        .overrideTemplate(DiasAtendimentoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DiasAtendimentoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DiasAtendimentoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DiasAtendimento(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.diasAtendimentos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
