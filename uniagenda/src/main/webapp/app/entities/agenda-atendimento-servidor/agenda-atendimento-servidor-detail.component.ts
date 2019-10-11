import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgendaAtendimentoServidor } from 'app/shared/model/agenda-atendimento-servidor.model';

@Component({
  selector: 'jhi-agenda-atendimento-servidor-detail',
  templateUrl: './agenda-atendimento-servidor-detail.component.html'
})
export class AgendaAtendimentoServidorDetailComponent implements OnInit {
  agendaAtendimentoServidor: IAgendaAtendimentoServidor;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agendaAtendimentoServidor }) => {
      this.agendaAtendimentoServidor = agendaAtendimentoServidor;
    });
  }

  previousState() {
    window.history.back();
  }
}
