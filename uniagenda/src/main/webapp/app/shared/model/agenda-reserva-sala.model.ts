import { IAgendaSala } from 'app/shared/model/agenda-sala.model';
import { IAluno } from 'app/shared/model/aluno.model';
import { StatusAgenda } from 'app/shared/model/enumerations/status-agenda.model';

export interface IAgendaReservaSala {
  id?: number;
  status?: StatusAgenda;
  horarios?: IAgendaSala[];
  mes?: IAgendaSala[];
  diaMes?: IAgendaSala[];
  statuses?: IAgendaSala[];
  raAlunos?: IAluno[];
  codigoSalas?: IAgendaSala[];
}

export class AgendaReservaSala implements IAgendaReservaSala {
  constructor(
    public id?: number,
    public status?: StatusAgenda,
    public horarios?: IAgendaSala[],
    public mes?: IAgendaSala[],
    public diaMes?: IAgendaSala[],
    public statuses?: IAgendaSala[],
    public raAlunos?: IAluno[],
    public codigoSalas?: IAgendaSala[]
  ) {}
}
