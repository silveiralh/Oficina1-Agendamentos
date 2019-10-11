import { IAluno } from 'app/shared/model/aluno.model';
import { IAgendaServidor } from 'app/shared/model/agenda-servidor.model';
import { StatusAgenda } from 'app/shared/model/enumerations/status-agenda.model';

export interface IAgendaAtendimentoServidor {
  id?: number;
  status?: StatusAgenda;
  aluno?: IAluno;
  agendaServidor?: IAgendaServidor;
  agendaServidor?: IAgendaServidor;
  agendaServidor?: IAgendaServidor;
  agendaServidor?: IAgendaServidor;
  agendaServidor?: IAgendaServidor;
}

export class AgendaAtendimentoServidor implements IAgendaAtendimentoServidor {
  constructor(
    public id?: number,
    public status?: StatusAgenda,
    public aluno?: IAluno,
    public agendaServidor?: IAgendaServidor,
    public agendaServidor?: IAgendaServidor,
    public agendaServidor?: IAgendaServidor,
    public agendaServidor?: IAgendaServidor,
    public agendaServidor?: IAgendaServidor
  ) {}
}
