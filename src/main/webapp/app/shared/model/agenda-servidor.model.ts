import { IAgendaAtendimentoServidor } from 'app/shared/model/agenda-atendimento-servidor.model';
import { IServidor } from 'app/shared/model/servidor.model';
import { StatusAgenda } from 'app/shared/model/enumerations/status-agenda.model';
import { Horario } from 'app/shared/model/enumerations/horario.model';

export interface IAgendaServidor {
  id?: number;
  status?: StatusAgenda;
  horario?: Horario;
  agendaAtendimentoServidor?: IAgendaAtendimentoServidor;
  codSiapes?: IServidor[];
}

export class AgendaServidor implements IAgendaServidor {
  constructor(
    public id?: number,
    public status?: StatusAgenda,
    public horario?: Horario,
    public agendaAtendimentoServidor?: IAgendaAtendimentoServidor,
    public codSiapes?: IServidor[]
  ) {}
}
