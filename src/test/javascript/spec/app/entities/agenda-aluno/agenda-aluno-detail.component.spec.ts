import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UniagendaTestModule } from '../../../test.module';
import { AgendaAlunoDetailComponent } from 'app/entities/agenda-aluno/agenda-aluno-detail.component';
import { AgendaAluno } from 'app/shared/model/agenda-aluno.model';

describe('Component Tests', () => {
  describe('AgendaAluno Management Detail Component', () => {
    let comp: AgendaAlunoDetailComponent;
    let fixture: ComponentFixture<AgendaAlunoDetailComponent>;
    const route = ({ data: of({ agendaAluno: new AgendaAluno(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UniagendaTestModule],
        declarations: [AgendaAlunoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AgendaAlunoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgendaAlunoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.agendaAluno).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
