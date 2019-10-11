import { IDiasAtendimento } from 'app/shared/model/dias-atendimento.model';
import { ISala } from 'app/shared/model/sala.model';
import { IAgendaReservaSala } from 'app/shared/model/agenda-reserva-sala.model';
import { StatusAgenda } from 'app/shared/model/enumerations/status-agenda.model';
import { Horario } from 'app/shared/model/enumerations/horario.model';

export interface IAgendaSala {
  id?: number;
  status?: StatusAgenda;
  horario?: Horario;
  mes?: IDiasAtendimento[];
  diaMes?: IDiasAtendimento[];
  statusDias?: IDiasAtendimento[];
  codigoSalas?: ISala[];
  agendaReservaSala?: IAgendaReservaSala
 
}

export class AgendaSala implements IAgendaSala {
  constructor(
    public id?: number,
    public status?: StatusAgenda,
    public horario?: Horario,
    public mes?: IDiasAtendimento[],
    public diaMes?: IDiasAtendimento[],
    public statusDias?: IDiasAtendimento[],
    public codigoSalas?: ISala[],
    public agendaReservaSala?: IAgendaReservaSala
  ) {}
}
