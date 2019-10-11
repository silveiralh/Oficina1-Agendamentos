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
import { ISala } from 'app/shared/model/sala.model';
import { SalaService } from 'app/entities/sala/sala.service';
import { IDiasAtendimento } from 'app/shared/model/dias-atendimento.model';
import { DiasAtendimentoService } from 'app/entities/dias-atendimento/dias-atendimento.service';

@Component({
  selector: 'jhi-agenda-sala-update',
  templateUrl: './agenda-sala-update.component.html'
})
export class AgendaSalaUpdateComponent implements OnInit {
  isSaving: boolean;

  salas: ISala[];

  diasatendimentos: IDiasAtendimento[];

  editForm = this.fb.group({
    id: [],
    status: [],
    horario: [],
    sala: [],
    diasAtendimento: [],
    diasAtendimento: [],
    diasAtendimento: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected agendaSalaService: AgendaSalaService,
    protected salaService: SalaService,
    protected diasAtendimentoService: DiasAtendimentoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ agendaSala }) => {
      this.updateForm(agendaSala);
    });
    this.salaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISala[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISala[]>) => response.body)
      )
      .subscribe((res: ISala[]) => (this.salas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.diasAtendimentoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDiasAtendimento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDiasAtendimento[]>) => response.body)
      )
      .subscribe((res: IDiasAtendimento[]) => (this.diasatendimentos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(agendaSala: IAgendaSala) {
    this.editForm.patchValue({
      id: agendaSala.id,
      status: agendaSala.status,
      horario: agendaSala.horario,
      sala: agendaSala.sala,
      diasAtendimento: agendaSala.diasAtendimento,
      diasAtendimento: agendaSala.diasAtendimento,
      diasAtendimento: agendaSala.diasAtendimento
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
      sala: this.editForm.get(['sala']).value,
      diasAtendimento: this.editForm.get(['diasAtendimento']).value,
      diasAtendimento: this.editForm.get(['diasAtendimento']).value,
      diasAtendimento: this.editForm.get(['diasAtendimento']).value
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

  trackSalaById(index: number, item: ISala) {
    return item.id;
  }

  trackDiasAtendimentoById(index: number, item: IDiasAtendimento) {
    return item.id;
  }
}
