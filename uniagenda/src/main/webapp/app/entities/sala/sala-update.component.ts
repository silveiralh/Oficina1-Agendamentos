import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISala, Sala } from 'app/shared/model/sala.model';
import { SalaService } from './sala.service';
import { IAgendaSala } from 'app/shared/model/agenda-sala.model';
import { AgendaSalaService } from 'app/entities/agenda-sala/agenda-sala.service';

@Component({
  selector: 'jhi-sala-update',
  templateUrl: './sala-update.component.html'
})
export class SalaUpdateComponent implements OnInit {
  isSaving: boolean;

  agendasalas: IAgendaSala[];

  editForm = this.fb.group({
    id: [],
    nomeSala: [],
    codigoSala: [],
    agendaSala: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected salaService: SalaService,
    protected agendaSalaService: AgendaSalaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sala }) => {
      this.updateForm(sala);
    });
    this.agendaSalaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgendaSala[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgendaSala[]>) => response.body)
      )
      .subscribe((res: IAgendaSala[]) => (this.agendasalas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(sala: ISala) {
    this.editForm.patchValue({
      id: sala.id,
      nomeSala: sala.nomeSala,
      codigoSala: sala.codigoSala,
      agendaSala: sala.agendaSala
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
      codigoSala: this.editForm.get(['codigoSala']).value,
      agendaSala: this.editForm.get(['agendaSala']).value
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
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAgendaSalaById(index: number, item: IAgendaSala) {
    return item.id;
  }
}
