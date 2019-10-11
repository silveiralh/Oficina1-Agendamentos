import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICargo, Cargo } from 'app/shared/model/cargo.model';
import { CargoService } from './cargo.service';
import { IServidor } from 'app/shared/model/servidor.model';
import { ServidorService } from 'app/entities/servidor/servidor.service';

@Component({
  selector: 'jhi-cargo-update',
  templateUrl: './cargo-update.component.html'
})
export class CargoUpdateComponent implements OnInit {
  isSaving: boolean;

  servidors: IServidor[];

  editForm = this.fb.group({
    id: [],
    nomeCargo: [],
    servidor: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected cargoService: CargoService,
    protected servidorService: ServidorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cargo }) => {
      this.updateForm(cargo);
    });
    this.servidorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServidor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServidor[]>) => response.body)
      )
      .subscribe((res: IServidor[]) => (this.servidors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(cargo: ICargo) {
    this.editForm.patchValue({
      id: cargo.id,
      nomeCargo: cargo.nomeCargo,
      servidor: cargo.servidor
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
      nomeCargo: this.editForm.get(['nomeCargo']).value,
      servidor: this.editForm.get(['servidor']).value
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
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackServidorById(index: number, item: IServidor) {
    return item.id;
  }
}
