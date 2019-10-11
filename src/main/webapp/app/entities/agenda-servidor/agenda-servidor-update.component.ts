import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAgendaServidor, AgendaServidor } from 'app/shared/model/agenda-servidor.model';
import { AgendaServidorService } from './agenda-servidor.service';
import { IAgendaAtendimentoServidor } from 'app/shared/model/agenda-atendimento-servidor.model';
import { AgendaAtendimentoServidorService } from 'app/entities/agenda-atendimento-servidor/agenda-atendimento-servidor.service';

@Component({
  selector: 'jhi-agenda-servidor-update',
  templateUrl: './agenda-servidor-update.component.html'
})
export class AgendaServidorUpdateComponent implements OnInit {
  isSaving: boolean;

  agendaatendimentoservidors: IAgendaAtendimentoServidor[];

  editForm = this.fb.group({
    id: [],
    status: [],
    horario: [],
    agendaAtendimentoServidor: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected agendaServidorService: AgendaServidorService,
    protected agendaAtendimentoServidorService: AgendaAtendimentoServidorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ agendaServidor }) => {
      this.updateForm(agendaServidor);
    });
    this.agendaAtendimentoServidorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgendaAtendimentoServidor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgendaAtendimentoServidor[]>) => response.body)
      )
      .subscribe(
        (res: IAgendaAtendimentoServidor[]) => (this.agendaatendimentoservidors = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(agendaServidor: IAgendaServidor) {
    this.editForm.patchValue({
      id: agendaServidor.id,
      status: agendaServidor.status,
      horario: agendaServidor.horario,
      agendaAtendimentoServidor: agendaServidor.agendaAtendimentoServidor
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const agendaServidor = this.createFromForm();
    if (agendaServidor.id !== undefined) {
      this.subscribeToSaveResponse(this.agendaServidorService.update(agendaServidor));
    } else {
      this.subscribeToSaveResponse(this.agendaServidorService.create(agendaServidor));
    }
  }

  private createFromForm(): IAgendaServidor {
    return {
      ...new AgendaServidor(),
      id: this.editForm.get(['id']).value,
      status: this.editForm.get(['status']).value,
      horario: this.editForm.get(['horario']).value,
      agendaAtendimentoServidor: this.editForm.get(['agendaAtendimentoServidor']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgendaServidor>>) {
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

  trackAgendaAtendimentoServidorById(index: number, item: IAgendaAtendimentoServidor) {
    return item.id;
  }
}
