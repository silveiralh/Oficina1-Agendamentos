import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAgendaServidor, AgendaServidor } from 'app/shared/model/agenda-servidor.model';
import { AgendaServidorService } from './agenda-servidor.service';
import { IServidor } from 'app/shared/model/servidor.model';
import { ServidorService } from 'app/entities/servidor/servidor.service';

@Component({
  selector: 'jhi-agenda-servidor-update',
  templateUrl: './agenda-servidor-update.component.html'
})
export class AgendaServidorUpdateComponent implements OnInit {
  isSaving: boolean;

  servidors: IServidor[];

  editForm = this.fb.group({
    id: [],
    mes: [],
    horario: [],
    diaMes: [],
    diaSemana: [],
    statusDia: [],
    servidor: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected agendaServidorService: AgendaServidorService,
    protected servidorService: ServidorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ agendaServidor }) => {
      this.updateForm(agendaServidor);
    });
    this.servidorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServidor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServidor[]>) => response.body)
      )
      .subscribe((res: IServidor[]) => (this.servidors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(agendaServidor: IAgendaServidor) {
    this.editForm.patchValue({
      id: agendaServidor.id,
      mes: agendaServidor.mes,
      horario: agendaServidor.horario,
      diaMes: agendaServidor.diaMes,
      diaSemana: agendaServidor.diaSemana,
      statusDia: agendaServidor.statusDia,
      servidor: agendaServidor.servidor
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const agendaServidor = this.createFromForm();
    if (agendaServidor.id !== undefined) {
      this.subscribeToSaveResponse(this.agendaServidorService.update(agendaServidor));
    } else {
      this.subscribeToSaveResponse(this.agendaServidorService.create(agendaServidor));
    }
  }

  private createFromForm(): IAgendaServidor {
    return {
      ...new AgendaServidor(),
      id: this.editForm.get(['id']).value,
      mes: this.editForm.get(['mes']).value,
      horario: this.editForm.get(['horario']).value,
      diaMes: this.editForm.get(['diaMes']).value,
      diaSemana: this.editForm.get(['diaSemana']).value,
      statusDia: this.editForm.get(['statusDia']).value,
      servidor: this.editForm.get(['servidor']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgendaServidor>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackServidorById(index: number, item: IServidor) {
    return item.id;
  }
}
