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
import { IAgendaServidor } from 'app/shared/model/agenda-servidor.model';
import { AgendaServidorService } from 'app/entities/agenda-servidor/agenda-servidor.service';

@Component({
  selector: 'jhi-servidor-update',
  templateUrl: './servidor-update.component.html'
})
export class ServidorUpdateComponent implements OnInit {
  isSaving: boolean;

  agendaservidors: IAgendaServidor[];

  editForm = this.fb.group({
    id: [],
    codSiape: [],
    nomeServidor: [],
    agendaServidor: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected servidorService: ServidorService,
    protected agendaServidorService: AgendaServidorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ servidor }) => {
      this.updateForm(servidor);
    });
    this.agendaServidorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgendaServidor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgendaServidor[]>) => response.body)
      )
      .subscribe((res: IAgendaServidor[]) => (this.agendaservidors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(servidor: IServidor) {
    this.editForm.patchValue({
      id: servidor.id,
      codSiape: servidor.codSiape,
      nomeServidor: servidor.nomeServidor,
      agendaServidor: servidor.agendaServidor
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
      agendaServidor: this.editForm.get(['agendaServidor']).value
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

  trackAgendaServidorById(index: number, item: IAgendaServidor) {
    return item.id;
  }
}
