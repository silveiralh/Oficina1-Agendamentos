import { ISala } from 'app/shared/model/sala.model';
import { IAgendaReservaSala } from 'app/shared/model/agenda-reserva-sala.model';
import { IDiasAtendimento } from 'app/shared/model/dias-atendimento.model';
import { StatusAgenda } from 'app/shared/model/enumerations/status-agenda.model';
import { Horario } from 'app/shared/model/enumerations/horario.model';

export interface IAgendaSala {
  id?: number;
  status?: StatusAgenda;
  horario?: Horario;
  sala?: ISala;
  horarios?: IAgendaReservaSala[];
  mes?: IAgendaReservaSala[];
  diaMes?: IAgendaReservaSala[];
  statuses?: IAgendaReservaSala[];
  codigoSalas?: IAgendaReservaSala[];
  diasAtendimento?: IDiasAtendimento;
  diasAtendimento?: IDiasAtendimento;
  diasAtendimento?: IDiasAtendimento;
}

export class AgendaSala implements IAgendaSala {
  constructor(
    public id?: number,
    public status?: StatusAgenda,
    public horario?: Horario,
    public sala?: ISala,
    public horarios?: IAgendaReservaSala[],
    public mes?: IAgendaReservaSala[],
    public diaMes?: IAgendaReservaSala[],
    public statuses?: IAgendaReservaSala[],
    public codigoSalas?: IAgendaReservaSala[],
    public diasAtendimento?: IDiasAtendimento,
    public diasAtendimento?: IDiasAtendimento,
    public diasAtendimento?: IDiasAtendimento
  ) {}
}
