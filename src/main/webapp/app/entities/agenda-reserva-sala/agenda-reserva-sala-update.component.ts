import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAgendaReservaSala, AgendaReservaSala } from 'app/shared/model/agenda-reserva-sala.model';
import { AgendaReservaSalaService } from './agenda-reserva-sala.service';
import { IAluno } from 'app/shared/model/aluno.model';
import { AlunoService } from 'app/entities/aluno/aluno.service';
import { IAgendaSala } from 'app/shared/model/agenda-sala.model';
import { AgendaSalaService } from 'app/entities/agenda-sala/agenda-sala.service';

@Component({
  selector: 'jhi-agenda-reserva-sala-update',
  templateUrl: './agenda-reserva-sala-update.component.html'
})
export class AgendaReservaSalaUpdateComponent implements OnInit {
  isSaving: boolean;

  alunos: IAluno[];

  agendasalas: IAgendaSala[];

  editForm = this.fb.group({
    id: [],
    status: [],
    aluno: [],
    agendaSala: [],
    agendaSala: [],
    agendaSala: [],
    agendaSala: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected agendaReservaSalaService: AgendaReservaSalaService,
    protected alunoService: AlunoService,
    protected agendaSalaService: AgendaSalaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ agendaReservaSala }) => {
      this.updateForm(agendaReservaSala);
    });
    this.alunoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAluno[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAluno[]>) => response.body)
      )
      .subscribe((res: IAluno[]) => (this.alunos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.agendaSalaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgendaSala[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgendaSala[]>) => response.body)
      )
      .subscribe((res: IAgendaSala[]) => (this.agendasalas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(agendaReservaSala: IAgendaReservaSala) {
    this.editForm.patchValue({
      id: agendaReservaSala.id,
      status: agendaReservaSala.status,
      aluno: agendaReservaSala.aluno,
      agendaSala: agendaReservaSala.agendaSala,
      agendaSala: agendaReservaSala.agendaSala,
      agendaSala: agendaReservaSala.agendaSala,
      agendaSala: agendaReservaSala.agendaSala
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
      status: this.editForm.get(['status']).value,
      aluno: this.editForm.get(['aluno']).value,
      agendaSala: this.editForm.get(['agendaSala']).value,
      agendaSala: this.editForm.get(['agendaSala']).value,
      agendaSala: this.editForm.get(['agendaSala']).value,
      agendaSala: this.editForm.get(['agendaSala']).value
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
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAlunoById(index: number, item: IAluno) {
    return item.id;
  }

  trackAgendaSalaById(index: number, item: IAgendaSala) {
    return item.id;
  }
}
