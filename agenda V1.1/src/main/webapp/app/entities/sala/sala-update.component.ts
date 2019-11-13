import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISala, Sala } from 'app/shared/model/sala.model';
import { SalaService } from './sala.service';

@Component({
  selector: 'jhi-sala-update',
  templateUrl: './sala-update.component.html'
})
export class SalaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nomeSala: [],
    codigoSala: []
  });

  constructor(protected salaService: SalaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sala }) => {
      this.updateForm(sala);
    });
  }

  updateForm(sala: ISala) {
    this.editForm.patchValue({
      id: sala.id,
      nomeSala: sala.nomeSala,
      codigoSala: sala.codigoSala
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sala = this.createFromForm();
    if (sala.id !== undefined) {
      this.subscribeToSaveResponse(this.salaService.update(sala));
    } else {
      this.subscribeToSaveResponse(this.salaService.create(sala));
    }
  }

  private createFromForm(): ISala {
    return {
      ...new Sala(),
      id: this.editForm.get(['id']).value,
      nomeSala: this.editForm.get(['nomeSala']).value,
      codigoSala: this.editForm.get(['codigoSala']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISala>>) {
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
