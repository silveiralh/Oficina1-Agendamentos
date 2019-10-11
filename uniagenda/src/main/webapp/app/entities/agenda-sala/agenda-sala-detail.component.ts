import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgendaSala } from 'app/shared/model/agenda-sala.model';

@Component({
  selector: 'jhi-agenda-sala-detail',
  templateUrl: './agenda-sala-detail.component.html'
})
export class AgendaSalaDetailComponent implements OnInit {
  agendaSala: IAgendaSala;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agendaSala }) => {
      this.agendaSala = agendaSala;
    });
  }

  previousState() {
    window.history.back();
  }
}
