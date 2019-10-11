import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDiasAtendimento, DiasAtendimento } from 'app/shared/model/dias-atendimento.model';
import { DiasAtendimentoService } from './dias-atendimento.service';
import { IServidor } from 'app/shared/model/servidor.model';
import { ServidorService } from 'app/entities/servidor/servidor.service';

@Component({
  selector: 'jhi-dias-atendimento-update',
  templateUrl: './dias-atendimento-update.component.html'
})
export class DiasAtendimentoUpdateComponent implements OnInit {
  isSaving: boolean;

  servidors: IServidor[];

  editForm = this.fb.group({
    id: [],
    mes: [],
    diaMes: [],
    diaSemana: [],
    statusDia: [],
    servidor: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected diasAtendimentoService: DiasAtendimentoService,
    protected servidorService: ServidorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ diasAtendimento }) => {
      this.updateForm(diasAtendimento);
    });
    this.servidorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServidor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServidor[]>) => response.body)
      )
      .subscribe((res: IServidor[]) => (this.servidors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(diasAtendimento: IDiasAtendimento) {
    this.editForm.patchValue({
      id: diasAtendimento.id,
      mes: diasAtendimento.mes,
      diaMes: diasAtendimento.diaMes,
      diaSemana: diasAtendimento.diaSemana,
      statusDia: diasAtendimento.statusDia,
      servidor: diasAtendimento.servidor
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const diasAtendimento = this.createFromForm();
    if (diasAtendimento.id !== undefined) {
      this.subscribeToSaveResponse(this.diasAtendimentoService.update(diasAtendimento));
    } else {
      this.subscribeToSaveResponse(this.diasAtendimentoService.create(diasAtendimento));
    }
  }

  private createFromForm(): IDiasAtendimento {
    return {
      ...new DiasAtendimento(),
      id: this.editForm.get(['id']).value,
      mes: this.editForm.get(['mes']).value,
      diaMes: this.editForm.get(['diaMes']).value,
      diaSemana: this.editForm.get(['diaSemana']).value,
      statusDia: this.editForm.get(['statusDia']).value,
      servidor: this.editForm.get(['servidor']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiasAtendimento>>) {
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
