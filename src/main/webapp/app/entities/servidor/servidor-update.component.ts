import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IServidor, Servidor } from 'app/shared/model/servidor.model';
import { ServidorService } from './servidor.service';
import { ICargo } from 'app/shared/model/cargo.model';
import { CargoService } from 'app/entities/cargo/cargo.service';

@Component({
  selector: 'jhi-servidor-update',
  templateUrl: './servidor-update.component.html'
})
export class ServidorUpdateComponent implements OnInit {
  isSaving: boolean;

  cargos: ICargo[];

  editForm = this.fb.group({
    id: [],
    codSiape: [],
    nomeServidor: [],
    cargo: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected servidorService: ServidorService,
    protected cargoService: CargoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ servidor }) => {
      this.updateForm(servidor);
    });
    this.cargoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICargo[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICargo[]>) => response.body)
      )
      .subscribe((res: ICargo[]) => (this.cargos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(servidor: IServidor) {
    this.editForm.patchValue({
      id: servidor.id,
      codSiape: servidor.codSiape,
      nomeServidor: servidor.nomeServidor,
      cargo: servidor.cargo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const servidor = this.createFromForm();
    if (servidor.id !== undefined) {
      this.subscribeToSaveResponse(this.servidorService.update(servidor));
    } else {
      this.subscribeToSaveResponse(this.servidorService.create(servidor));
    }
  }

  private createFromForm(): IServidor {
    return {
      ...new Servidor(),
      id: this.editForm.get(['id']).value,
      codSiape: this.editForm.get(['codSiape']).value,
      nomeServidor: this.editForm.get(['nomeServidor']).value,
      cargo: this.editForm.get(['cargo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServidor>>) {
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

  trackCargoById(index: number, item: ICargo) {
    return item.id;
  }
}
