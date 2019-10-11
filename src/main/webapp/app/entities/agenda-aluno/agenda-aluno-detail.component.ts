import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgendaAluno } from 'app/shared/model/agenda-aluno.model';

@Component({
  selector: 'jhi-agenda-aluno-detail',
  templateUrl: './agenda-aluno-detail.component.html'
})
export class AgendaAlunoDetailComponent implements OnInit {
  agendaAluno: IAgendaAluno;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agendaAluno }) => {
      this.agendaAluno = agendaAluno;
    });
  }

  previousState() {
    window.history.back();
  }
}
