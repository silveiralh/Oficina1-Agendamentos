import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAgendaReservaSala, AgendaReservaSala } from 'app/shared/model/agenda-reserva-sala.model';
import { AgendaReservaSalaService } from './agenda-reserva-sala.service';

@Component({
  selector: 'jhi-agenda-reserva-sala-update',
  templateUrl: './agenda-reserva-sala-update.component.html'
})
export class AgendaReservaSalaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    status: []
  });

  constructor(
    protected agendaReservaSalaService: AgendaReservaSalaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ agendaReservaSala }) => {
      this.updateForm(agendaReservaSala);
    });
  }

  updateForm(agendaReservaSala: IAgendaReservaSala) {
    this.editForm.patchValue({
      id: agendaReservaSala.id,
      status: agendaReservaSala.status
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const agendaReservaSala = this.createFromForm();
    if (agendaReservaSala.id !== undefined) {
      this.subscribeToSaveResponse(this.agendaReservaSalaService.update(agendaReservaSala));
    } else {
      this.subscribeToSaveResponse(this.agendaReservaSalaService.create(agendaReservaSala));
    }
  }

  private createFromForm(): IAgendaReservaSala {
    return {
      ...new AgendaReservaSala(),
      id: this.editForm.get(['id']).value,
      status: this.editForm.get(['status']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgendaReservaSala>>) {
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
