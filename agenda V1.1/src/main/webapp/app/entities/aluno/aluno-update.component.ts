import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAluno, Aluno } from 'app/shared/model/aluno.model';
import { AlunoService } from './aluno.service';

@Component({
  selector: 'jhi-aluno-update',
  templateUrl: './aluno-update.component.html'
})
export class AlunoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    raAluno: [],
    nomeAluno: [],
    periodo: [],
    curso: []
  });

  constructor(protected alunoService: AlunoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ aluno }) => {
      this.updateForm(aluno);
    });
  }

  updateForm(aluno: IAluno) {
    this.editForm.patchValue({
      id: aluno.id,
      raAluno: aluno.raAluno,
      nomeAluno: aluno.nomeAluno,
      periodo: aluno.periodo,
      curso: aluno.curso
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
      curso: this.editForm.get(['curso']).value
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
}
