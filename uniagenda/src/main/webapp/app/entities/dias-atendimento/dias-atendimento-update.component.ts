import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDiasAtendimento, DiasAtendimento } from 'app/shared/model/dias-atendimento.model';
import { DiasAtendimentoService } from './dias-atendimento.service';
import { IAgendaServidor } from 'app/shared/model/agenda-servidor.model';
import { AgendaServidorService } from 'app/entities/agenda-servidor/agenda-servidor.service';
import { IAgendaAluno } from 'app/shared/model/agenda-aluno.model';
import { AgendaAlunoService } from 'app/entities/agenda-aluno/agenda-aluno.service';
import { IAgendaSala } from 'app/shared/model/agenda-sala.model';
import { AgendaSalaService } from 'app/entities/agenda-sala/agenda-sala.service';

@Component({
  selector: 'jhi-dias-atendimento-update',
  templateUrl: './dias-atendimento-update.component.html'
})
export class DiasAtendimentoUpdateComponent implements OnInit {
  isSaving: boolean;

  agendaservidors: IAgendaServidor[];

  agendaalunos: IAgendaAluno[];

  agendasalas: IAgendaSala[];

  editForm = this.fb.group({
    id: [],
    mes: [],
    diaMes: [],
    diaSemana: [],
    statusDia: [],
    agendaServidor: [],
    agendaAluno: [],
    agendaSala: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected diasAtendimentoService: DiasAtendimentoService,
    protected agendaServidorService: AgendaServidorService,
    protected agendaAlunoService: AgendaAlunoService,
    protected agendaSalaService: AgendaSalaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ diasAtendimento }) => {
      this.updateForm(diasAtendimento);
    });
    this.agendaServidorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgendaServidor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgendaServidor[]>) => response.body)
      )
      .subscribe((res: IAgendaServidor[]) => (this.agendaservidors = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.agendaAlunoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgendaAluno[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgendaAluno[]>) => response.body)
      )
      .subscribe((res: IAgendaAluno[]) => (this.agendaalunos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.agendaSalaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgendaSala[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgendaSala[]>) => response.body)
      )
      .subscribe((res: IAgendaSala[]) => (this.agendasalas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(diasAtendimento: IDiasAtendimento) {
    this.editForm.patchValue({
      id: diasAtendimento.id,
      mes: diasAtendimento.mes,
      diaMes: diasAtendimento.diaMes,
      diaSemana: diasAtendimento.diaSemana,
      statusDia: diasAtendimento.statusDia,
      agendaServidor: diasAtendimento.agendaServidor,
      agendaAluno: diasAtendimento.agendaAluno,
      agendaSala: diasAtendimento.agendaSala
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const diasAtendimento = this.createFromForm();
    if (diasAtendimento.id !== undefined) {
      this.subscribeToSaveResponse(this.diasAtendimentoService.update(diasAtendimento));
    } else {
      this.subscribeToSaveResponse(this.diasAtendimentoService.create(diasAtendimento));
    }
  }

  private createFromForm(): IDiasAtendimento {
    return {
      ...new DiasAtendimento(),
      id: this.editForm.get(['id']).value,
      mes: this.editForm.get(['mes']).value,
      diaMes: this.editForm.get(['diaMes']).value,
      diaSemana: this.editForm.get(['diaSemana']).value,
      statusDia: this.editForm.get(['statusDia']).value,
      agendaServidor: this.editForm.get(['agendaServidor']).value,
      agendaAluno: this.editForm.get(['agendaAluno']).value,
      agendaSala: this.editForm.get(['agendaSala']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiasAtendimento>>) {
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

  trackAgendaServidorById(index: number, item: IAgendaServidor) {
    return item.id;
  }

  trackAgendaAlunoById(index: number, item: IAgendaAluno) {
    return item.id;
  }

  trackAgendaSalaById(index: number, item: IAgendaSala) {
    return item.id;
  }
}
