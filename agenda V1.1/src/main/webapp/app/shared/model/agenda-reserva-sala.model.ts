import { IAluno } from 'app/shared/model/aluno.model';
import { IAgendaSala } from 'app/shared/model/agenda-sala.model';
import { StatusAgenda } from 'app/shared/model/enumerations/status-agenda.model';

export interface IAgendaReservaSala {
  id?: number;
  status?: StatusAgenda;
  aluno?: IAluno;
  agendaSala?: IAgendaSala;
}

export class AgendaReservaSala implements IAgendaReservaSala {
  constructor(public id?: number, public status?: StatusAgenda, public aluno?: IAluno, public agendaSala?: IAgendaSala) {}
}
