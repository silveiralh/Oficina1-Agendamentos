import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgendaServidor } from 'app/shared/model/agenda-servidor.model';

@Component({
  selector: 'jhi-agenda-servidor-detail',
  templateUrl: './agenda-servidor-detail.component.html'
})
export class AgendaServidorDetailComponent implements OnInit {
  agendaServidor: IAgendaServidor;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agendaServidor }) => {
      this.agendaServidor = agendaServidor;
    });
  }

  previousState() {
    window.history.back();
  }
}
