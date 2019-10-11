import { IAgendaAluno } from 'app/shared/model/agenda-aluno.model';
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
  agendaAluno?: IAgendaAluno;
}

export class DiasAtendimento implements IDiasAtendimento {
  constructor(
    public id?: number,
    public mes?: Mes,
    public diaMes?: DiaMes,
    public diaSemana?: DiaSemana,
    public statusDia?: StatusDia,
    public agendaAluno?: IAgendaAluno
  ) {}
}
