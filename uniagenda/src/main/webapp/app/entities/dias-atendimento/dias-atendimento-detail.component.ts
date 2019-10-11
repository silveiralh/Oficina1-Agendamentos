import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDiasAtendimento } from 'app/shared/model/dias-atendimento.model';

@Component({
  selector: 'jhi-dias-atendimento-detail',
  templateUrl: './dias-atendimento-detail.component.html'
})
export class DiasAtendimentoDetailComponent implements OnInit {
  diasAtendimento: IDiasAtendimento;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ diasAtendimento }) => {
      this.diasAtendimento = diasAtendimento;
    });
  }

  previousState() {
    window.history.back();
  }
}
