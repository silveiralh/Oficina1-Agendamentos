import { IAgendaServidor } from 'app/shared/model/agenda-servidor.model';
import { IAgendaAluno } from 'app/shared/model/agenda-aluno.model';
import { IAgendaSala } from 'app/shared/model/agenda-sala.model';
import { IServidor } from 'app/shared/model/servidor.model';
import { Mes } from 'app/shared/model/enumerations/mes.model';
import { DiaMes } from 'app/shared/model/enumerations/dia-mes.model';
import { DiaSemana } from 'app/shared/model/enumerations/dia-semana.model';
import { StatusDia } from 'app/shared/model/enumerations/status-dia.model';

export interface IDiasAtendimento {
  id?: number;
  mes?: Mes;
  diaMes?: DiaMes;
  diaSemana?: DiaSemana;
  statusDia?: StatusDia;
  agendaServidor?: IAgendaServidor;
  agendaAluno?: IAgendaAluno;
  agendaSala?: IAgendaSala;
  codSiapes?: IServidor[];
}

export class DiasAtendimento implements IDiasAtendimento {
  constructor(
    public id?: number,
    public mes?: Mes,
    public diaMes?: DiaMes,
    public diaSemana?: DiaSemana,
    public statusDia?: StatusDia,
    public agendaServidor?: IAgendaServidor,
    public agendaAluno?: IAgendaAluno,
    public agendaSala?: IAgendaSala,
    public codSiapes?: IServidor[]
  ) {}
}
