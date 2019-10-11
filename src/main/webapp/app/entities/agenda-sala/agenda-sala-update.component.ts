import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAgendaSala, AgendaSala } from 'app/shared/model/agenda-sala.model';
import { AgendaSalaService } from './agenda-sala.service';
import { IAgendaReservaSala } from 'app/shared/model/agenda-reserva-sala.model';
import { AgendaReservaSalaService } from 'app/entities/agenda-reserva-sala/agenda-reserva-sala.service';

@Component({
  selector: 'jhi-agenda-sala-update',
  templateUrl: './agenda-sala-update.component.html'
})
export class AgendaSalaUpdateComponent implements OnInit {
  isSaving: boolean;

  agendareservasalas: IAgendaReservaSala[];

  editForm = this.fb.group({
    id: [],
    status: [],
    horario: [],
    agendaReservaSala: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected agendaSalaService: AgendaSalaService,
    protected agendaReservaSalaService: AgendaReservaSalaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ agendaSala }) => {
      this.updateForm(agendaSala);
    });
    this.agendaReservaSalaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgendaReservaSala[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgendaReservaSala[]>) => response.body)
      )
      .subscribe((res: IAgendaReservaSala[]) => (this.agendareservasalas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(agendaSala: IAgendaSala) {
    this.editForm.patchValue({
      id: agendaSala.id,
      status: agendaSala.status,
      horario: agendaSala.horario,
      agendaReservaSala: agendaSala.agendaReservaSala
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const agendaSala = this.createFromForm();
    if (agendaSala.id !== undefined) {
      this.subscribeToSaveResponse(this.agendaSalaService.update(agendaSala));
    } else {
      this.subscribeToSaveResponse(this.agendaSalaService.create(agendaSala));
    }
  }

  private createFromForm(): IAgendaSala {
    return {
      ...new AgendaSala(),
      id: this.editForm.get(['id']).value,
      status: this.editForm.get(['status']).value,
      horario: this.editForm.get(['horario']).value,
      agendaReservaSala: this.editForm.get(['agendaReservaSala']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgendaSala>>) {
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

  trackAgendaReservaSalaById(index: number, item: IAgendaReservaSala) {
    return item.id;
  }
}
