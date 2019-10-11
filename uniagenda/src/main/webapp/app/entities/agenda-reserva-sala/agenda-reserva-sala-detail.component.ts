import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgendaReservaSala } from 'app/shared/model/agenda-reserva-sala.model';

@Component({
  selector: 'jhi-agenda-reserva-sala-detail',
  templateUrl: './agenda-reserva-sala-detail.component.html'
})
export class AgendaReservaSalaDetailComponent implements OnInit {
  agendaReservaSala: IAgendaReservaSala;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agendaReservaSala }) => {
      this.agendaReservaSala = agendaReservaSala;
    });
  }

  previousState() {
    window.history.back();
  }
}
