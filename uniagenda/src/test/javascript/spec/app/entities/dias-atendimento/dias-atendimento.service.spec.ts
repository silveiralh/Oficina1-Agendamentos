import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { DiasAtendimentoService } from 'app/entities/dias-atendimento/dias-atendimento.service';
import { IDiasAtendimento, DiasAtendimento } from 'app/shared/model/dias-atendimento.model';
import { Mes } from 'app/shared/model/enumerations/mes.model';
import { DiaMes } from 'app/shared/model/enumerations/dia-mes.model';
import { DiaSemana } from 'app/shared/model/enumerations/dia-semana.model';
import { StatusDia } from 'app/shared/model/enumerations/status-dia.model';

describe('Service Tests', () => {
  describe('DiasAtendimento Service', () => {
    let injector: TestBed;
    let service: DiasAtendimentoService;
    let httpMock: HttpTestingController;
    let elemDefault: IDiasAtendimento;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DiasAtendimentoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DiasAtendimento(0, Mes.Janeiro, DiaMes.D1, DiaSemana.Segunda_feira, StatusDia.Disponivel);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a DiasAtendimento', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new DiasAtendimento(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a DiasAtendimento', () => {
        const returnedFromService = Object.assign(
          {
            mes: 'BBBBBB',
            diaMes: 'BBBBBB',
            diaSemana: 'BBBBBB',
            statusDia: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of DiasAtendimento', () => {
        const returnedFromService = Object.assign(
          {
            mes: 'BBBBBB',
            diaMes: 'BBBBBB',
            diaSemana: 'BBBBBB',
            statusDia: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DiasAtendimento', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
