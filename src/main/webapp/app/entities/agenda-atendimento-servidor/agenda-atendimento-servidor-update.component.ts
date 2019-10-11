import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAgendaAtendimentoServidor, AgendaAtendimentoServidor } from 'app/shared/model/agenda-atendimento-servidor.model';
import { AgendaAtendimentoServidorService } from './agenda-atendimento-servidor.service';
import { IAluno } from 'app/shared/model/aluno.model';
import { AlunoService } from 'app/entities/aluno/aluno.service';
import { IAgendaServidor } from 'app/shared/model/agenda-servidor.model';
import { AgendaServidorService } from 'app/entities/agenda-servidor/agenda-servidor.service';

@Component({
  selector: 'jhi-agenda-atendimento-servidor-update',
  templateUrl: './agenda-atendimento-servidor-update.component.html'
})
export class AgendaAtendimentoServidorUpdateComponent implements OnInit {
  isSaving: boolean;

  alunos: IAluno[];

  agendaservidors: IAgendaServidor[];

  editForm = this.fb.group({
    id: [],
    status: [],
    aluno: [],
    agendaServidor: [],
    agendaServidor: [],
    agendaServidor: [],
    agendaServidor: [],
    agendaServidor: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected agendaAtendimentoServidorService: AgendaAtendimentoServidorService,
    protected alunoService: AlunoService,
    protected agendaServidorService: AgendaServidorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ agendaAtendimentoServidor }) => {
      this.updateForm(agendaAtendimentoServidor);
    });
    this.alunoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAluno[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAluno[]>) => response.body)
      )
      .subscribe((res: IAluno[]) => (this.alunos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.agendaServidorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgendaServidor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgendaServidor[]>) => response.body)
      )
      .subscribe((res: IAgendaServidor[]) => (this.agendaservidors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(agendaAtendimentoServidor: IAgendaAtendimentoServidor) {
    this.editForm.patchValue({
      id: agendaAtendimentoServidor.id,
      status: agendaAtendimentoServidor.status,
      aluno: agendaAtendimentoServidor.aluno,
      agendaServidor: agendaAtendimentoServidor.agendaServidor,
      agendaServidor: agendaAtendimentoServidor.agendaServidor,
      agendaServidor: agendaAtendimentoServidor.agendaServidor,
      agendaServidor: agendaAtendimentoServidor.agendaServidor,
      agendaServidor: agendaAtendimentoServidor.agendaServidor
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const agendaAtendimentoServidor = this.createFromForm();
    if (agendaAtendimentoServidor.id !== undefined) {
      this.subscribeToSaveResponse(this.agendaAtendimentoServidorService.update(agendaAtendimentoServidor));
    } else {
      this.subscribeToSaveResponse(this.agendaAtendimentoServidorService.create(agendaAtendimentoServidor));
    }
  }

  private createFromForm(): IAgendaAtendimentoServidor {
    return {
      ...new AgendaAtendimentoServidor(),
      id: this.editForm.get(['id']).value,
      status: this.editForm.get(['status']).value,
      aluno: this.editForm.get(['aluno']).value,
      agendaServidor: this.editForm.get(['agendaServidor']).value,
      agendaServidor: this.editForm.get(['agendaServidor']).value,
      agendaServidor: this.editForm.get(['agendaServidor']).value,
      agendaServidor: this.editForm.get(['agendaServidor']).value,
      agendaServidor: this.editForm.get(['agendaServidor']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgendaAtendimentoServidor>>) {
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

  trackAlunoById(index: number, item: IAluno) {
    return item.id;
  }

  trackAgendaServidorById(index: number, item: IAgendaServidor) {
    return item.id;
  }
}
