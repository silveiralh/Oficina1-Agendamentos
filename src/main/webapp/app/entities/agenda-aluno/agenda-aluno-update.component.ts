import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAgendaAluno, AgendaAluno } from 'app/shared/model/agenda-aluno.model';
import { AgendaAlunoService } from './agenda-aluno.service';
import { IAluno } from 'app/shared/model/aluno.model';
import { AlunoService } from 'app/entities/aluno/aluno.service';
import { IDiasAtendimento } from 'app/shared/model/dias-atendimento.model';
import { DiasAtendimentoService } from 'app/entities/dias-atendimento/dias-atendimento.service';

@Component({
  selector: 'jhi-agenda-aluno-update',
  templateUrl: './agenda-aluno-update.component.html'
})
export class AgendaAlunoUpdateComponent implements OnInit {
  isSaving: boolean;

  alunos: IAluno[];

  diasatendimentos: IDiasAtendimento[];

  editForm = this.fb.group({
    id: [],
    status: [],
    horario: [],
    aluno: [],
    diasAtendimento: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected agendaAlunoService: AgendaAlunoService,
    protected alunoService: AlunoService,
    protected diasAtendimentoService: DiasAtendimentoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ agendaAluno }) => {
      this.updateForm(agendaAluno);
    });
    this.alunoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAluno[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAluno[]>) => response.body)
      )
      .subscribe((res: IAluno[]) => (this.alunos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.diasAtendimentoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDiasAtendimento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDiasAtendimento[]>) => response.body)
      )
      .subscribe((res: IDiasAtendimento[]) => (this.diasatendimentos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(agendaAluno: IAgendaAluno) {
    this.editForm.patchValue({
      id: agendaAluno.id,
      status: agendaAluno.status,
      horario: agendaAluno.horario,
      aluno: agendaAluno.aluno,
      diasAtendimento: agendaAluno.diasAtendimento
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const agendaAluno = this.createFromForm();
    if (agendaAluno.id !== undefined) {
      this.subscribeToSaveResponse(this.agendaAlunoService.update(agendaAluno));
    } else {
      this.subscribeToSaveResponse(this.agendaAlunoService.create(agendaAluno));
    }
  }

  private createFromForm(): IAgendaAluno {
    return {
      ...new AgendaAluno(),
      id: this.editForm.get(['id']).value,
      status: this.editForm.get(['status']).value,
      horario: this.editForm.get(['horario']).value,
      aluno: this.editForm.get(['aluno']).value,
      diasAtendimento: this.editForm.get(['diasAtendimento']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgendaAluno>>) {
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

  trackDiasAtendimentoById(index: number, item: IDiasAtendimento) {
    return item.id;
  }
}
