import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UniAgendaTestModule } from '../../../test.module';
import { AgendaServidorDetailComponent } from 'app/entities/agenda-servidor/agenda-servidor-detail.component';
import { AgendaServidor } from 'app/shared/model/agenda-servidor.model';

describe('Component Tests', () => {
  describe('AgendaServidor Management Detail Component', () => {
    let comp: AgendaServidorDetailComponent;
    let fixture: ComponentFixture<AgendaServidorDetailComponent>;
    const route = ({ data: of({ agendaServidor: new AgendaServidor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UniAgendaTestModule],
        declarations: [AgendaServidorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AgendaServidorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgendaServidorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.agendaServidor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
