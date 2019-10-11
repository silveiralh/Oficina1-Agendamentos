import { IServidor } from 'app/shared/model/servidor.model';
import { IAgendaAtendimentoServidor } from 'app/shared/model/agenda-atendimento-servidor.model';
import { IDiasAtendimento } from 'app/shared/model/dias-atendimento.model';
import { StatusAgenda } from 'app/shared/model/enumerations/status-agenda.model';
import { Horario } from 'app/shared/model/enumerations/horario.model';

export interface IAgendaServidor {
  id?: number;
  status?: StatusAgenda;
  horario?: Horario;
  servidor?: IServidor;
  codSiapes?: IAgendaAtendimentoServidor[];
  horarios?: IAgendaAtendimentoServidor[];
  mes?: IAgendaAtendimentoServidor[];
  diaMes?: IAgendaAtendimentoServidor[];
  statuses?: IAgendaAtendimentoServidor[];
  diasAtendimento?: IDiasAtendimento;
  diasAtendimento?: IDiasAtendimento;
  diasAtendimento?: IDiasAtendimento;
}

export class AgendaServidor implements IAgendaServidor {
  constructor(
    public id?: number,
    public status?: StatusAgenda,
    public horario?: Horario,
    public servidor?: IServidor,
    public codSiapes?: IAgendaAtendimentoServidor[],
    public horarios?: IAgendaAtendimentoServidor[],
    public mes?: IAgendaAtendimentoServidor[],
    public diaMes?: IAgendaAtendimentoServidor[],
    public statuses?: IAgendaAtendimentoServidor[],
    public diasAtendimento?: IDiasAtendimento,
    public diasAtendimento?: IDiasAtendimento,
    public diasAtendimento?: IDiasAtendimento
  ) {}
}
