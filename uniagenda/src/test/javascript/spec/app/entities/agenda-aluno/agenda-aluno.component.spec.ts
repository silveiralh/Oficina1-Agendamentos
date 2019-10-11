import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UniAgendaTestModule } from '../../../test.module';
import { AgendaAlunoComponent } from 'app/entities/agenda-aluno/agenda-aluno.component';
import { AgendaAlunoService } from 'app/entities/agenda-aluno/agenda-aluno.service';
import { AgendaAluno } from 'app/shared/model/agenda-aluno.model';

describe('Component Tests', () => {
  describe('AgendaAluno Management Component', () => {
    let comp: AgendaAlunoComponent;
    let fixture: ComponentFixture<AgendaAlunoComponent>;
    let service: AgendaAlunoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UniAgendaTestModule],
        declarations: [AgendaAlunoComponent],
        providers: []
      })
        .overrideTemplate(AgendaAlunoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AgendaAlunoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgendaAlunoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AgendaAluno(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.agendaAlunos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
