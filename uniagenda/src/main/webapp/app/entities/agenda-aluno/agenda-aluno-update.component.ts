import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAgendaAluno, AgendaAluno } from 'app/shared/model/agenda-aluno.model';
import { AgendaAlunoService } from './agenda-aluno.service';

@Component({
  selector: 'jhi-agenda-aluno-update',
  templateUrl: './agenda-aluno-update.component.html'
})
export class AgendaAlunoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    status: [],
    horario: []
  });

  constructor(protected agendaAlunoService: AgendaAlunoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ agendaAluno }) => {
      this.updateForm(agendaAluno);
    });
  }

  updateForm(agendaAluno: IAgendaAluno) {
    this.editForm.patchValue({
      id: agendaAluno.id,
      status: agendaAluno.status,
      horario: agendaAluno.horario
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
      horario: this.editForm.get(['horario']).value
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
}
