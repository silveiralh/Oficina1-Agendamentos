import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICargo, Cargo } from 'app/shared/model/cargo.model';
import { CargoService } from './cargo.service';

@Component({
  selector: 'jhi-cargo-update',
  templateUrl: './cargo-update.component.html'
})
export class CargoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nomeCargo: []
  });

  constructor(protected cargoService: CargoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cargo }) => {
      this.updateForm(cargo);
    });
  }

  updateForm(cargo: ICargo) {
    this.editForm.patchValue({
      id: cargo.id,
      nomeCargo: cargo.nomeCargo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cargo = this.createFromForm();
    if (cargo.id !== undefined) {
      this.subscribeToSaveResponse(this.cargoService.update(cargo));
    } else {
      this.subscribeToSaveResponse(this.cargoService.create(cargo));
    }
  }

  private createFromForm(): ICargo {
    return {
      ...new Cargo(),
      id: this.editForm.get(['id']).value,
      nomeCargo: this.editForm.get(['nomeCargo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICargo>>) {
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
