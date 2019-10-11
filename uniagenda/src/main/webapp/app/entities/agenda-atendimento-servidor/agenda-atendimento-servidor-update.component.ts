import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAgendaAtendimentoServidor, AgendaAtendimentoServidor } from 'app/shared/model/agenda-atendimento-servidor.model';
import { AgendaAtendimentoServidorService } from './agenda-atendimento-servidor.service';

@Component({
  selector: 'jhi-agenda-atendimento-servidor-update',
  templateUrl: './agenda-atendimento-servidor-update.component.html'
})
export class AgendaAtendimentoServidorUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    status: []
  });

  constructor(
    protected agendaAtendimentoServidorService: AgendaAtendimentoServidorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ agendaAtendimentoServidor }) => {
      this.updateForm(agendaAtendimentoServidor);
    });
  }

  updateForm(agendaAtendimentoServidor: IAgendaAtendimentoServidor) {
    this.editForm.patchValue({
      id: agendaAtendimentoServidor.id,
      status: agendaAtendimentoServidor.status
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const agendaAtendimentoServidor = this.createFromForm();
    if (agendaAtendimentoServidor.id !== undefined) {
      this.subscribeToSaveResponse(this.agendaAtendimentoServidorService.update(agendaAtendimentoServidor));
    } else {
      this.subscribeToSaveResponse(this.agendaAtendimentoServidorService.create(agendaAtendimentoServidor));
    }
  }

  private createFromForm(): IAgendaAtendimentoServidor {
    return {
      ...new AgendaAtendimentoServidor(),
      id: this.editForm.get(['id']).value,
      status: this.editForm.get(['status']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgendaAtendimentoServidor>>) {
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
