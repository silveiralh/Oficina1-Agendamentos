import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAluno, Aluno } from 'app/shared/model/aluno.model';
import { AlunoService } from './aluno.service';
import { IAgendaAtendimentoServidor } from 'app/shared/model/agenda-atendimento-servidor.model';
import { AgendaAtendimentoServidorService } from 'app/entities/agenda-atendimento-servidor/agenda-atendimento-servidor.service';
import { IAgendaAluno } from 'app/shared/model/agenda-aluno.model';
import { AgendaAlunoService } from 'app/entities/agenda-aluno/agenda-aluno.service';
import { IAgendaReservaSala } from 'app/shared/model/agenda-reserva-sala.model';
import { AgendaReservaSalaService } from 'app/entities/agenda-reserva-sala/agenda-reserva-sala.service';

@Component({
  selector: 'jhi-aluno-update',
  templateUrl: './aluno-update.component.html'
})
export class AlunoUpdateComponent implements OnInit {
  isSaving: boolean;

  agendaatendimentoservidors: IAgendaAtendimentoServidor[];

  agendaalunos: IAgendaAluno[];

  agendareservasalas: IAgendaReservaSala[];

  editForm = this.fb.group({
    id: [],
    raAluno: [],
    nomeAluno: [],
    periodo: [],
    curso: [],
    agendaAtendimentoServidor: [],
    agendaAluno: [],
    agendaReservaSala: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected alunoService: AlunoService,
    protected agendaAtendimentoServidorService: AgendaAtendimentoServidorService,
    protected agendaAlunoService: AgendaAlunoService,
    protected agendaReservaSalaService: AgendaReservaSalaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ aluno }) => {
      this.updateForm(aluno);
    });
    this.agendaAtendimentoServidorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgendaAtendimentoServidor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgendaAtendimentoServidor[]>) => response.body)
      )
      .subscribe(
        (res: IAgendaAtendimentoServidor[]) => (this.agendaatendimentoservidors = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.agendaAlunoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgendaAluno[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgendaAluno[]>) => response.body)
      )
      .subscribe((res: IAgendaAluno[]) => (this.agendaalunos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.agendaReservaSalaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgendaReservaSala[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgendaReservaSala[]>) => response.body)
      )
      .subscribe((res: IAgendaReservaSala[]) => (this.agendareservasalas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(aluno: IAluno) {
    this.editForm.patchValue({
      id: aluno.id,
      raAluno: aluno.raAluno,
      nomeAluno: aluno.nomeAluno,
      periodo: aluno.periodo,
      curso: aluno.curso,
      agendaAtendimentoServidor: aluno.agendaAtendimentoServidor,
      agendaAluno: aluno.agendaAluno,
      agendaReservaSala: aluno.agendaReservaSala
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const aluno = this.createFromForm();
    if (aluno.id !== undefined) {
      this.subscribeToSaveResponse(this.alunoService.update(aluno));
    } else {
      this.subscribeToSaveResponse(this.alunoService.create(aluno));
    }
  }

  private createFromForm(): IAluno {
    return {
      ...new Aluno(),
      id: this.editForm.get(['id']).value,
      raAluno: this.editForm.get(['raAluno']).value,
      nomeAluno: this.editForm.get(['nomeAluno']).value,
      periodo: this.editForm.get(['periodo']).value,
      curso: this.editForm.get(['curso']).value,
      agendaAtendimentoServidor: this.editForm.get(['agendaAtendimentoServidor']).value,
      agendaAluno: this.editForm.get(['agendaAluno']).value,
      agendaReservaSala: this.editForm.get(['agendaReservaSala']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAluno>>) {
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

  trackAgendaAtendimentoServidorById(index: number, item: IAgendaAtendimentoServidor) {
    return item.id;
  }

  trackAgendaAlunoById(index: number, item: IAgendaAluno) {
    return item.id;
  }

  trackAgendaReservaSalaById(index: number, item: IAgendaReservaSala) {
    return item.id;
  }
}
