import { ISala } from 'app/shared/model/sala.model';
import { IAgendaReservaSala } from 'app/shared/model/agenda-reserva-sala.model';
import { StatusAgenda } from 'app/shared/model/enumerations/status-agenda.model';
import { Horario } from 'app/shared/model/enumerations/horario.model';

export interface IAgendaSala {
  id?: number;
  status?: StatusAgenda;
  horario?: Horario;
  codigoSalas?: ISala[];
  agendaReservaSala?: IAgendaReservaSala;
}

export class AgendaSala implements IAgendaSala {
  constructor(
    public id?: number,
    public status?: StatusAgenda,
    public horario?: Horario,
    public codigoSalas?: ISala[],
    public agendaReservaSala?: IAgendaReservaSala
  ) {}
}
