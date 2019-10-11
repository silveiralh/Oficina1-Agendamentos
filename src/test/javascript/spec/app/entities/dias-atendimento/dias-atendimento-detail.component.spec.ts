import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UniagendaTestModule } from '../../../test.module';
import { DiasAtendimentoDetailComponent } from 'app/entities/dias-atendimento/dias-atendimento-detail.component';
import { DiasAtendimento } from 'app/shared/model/dias-atendimento.model';

describe('Component Tests', () => {
  describe('DiasAtendimento Management Detail Component', () => {
    let comp: DiasAtendimentoDetailComponent;
    let fixture: ComponentFixture<DiasAtendimentoDetailComponent>;
    const route = ({ data: of({ diasAtendimento: new DiasAtendimento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UniagendaTestModule],
        declarations: [DiasAtendimentoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DiasAtendimentoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DiasAtendimentoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.diasAtendimento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
