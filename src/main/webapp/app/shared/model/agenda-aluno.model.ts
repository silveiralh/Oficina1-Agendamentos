import { IAluno } from 'app/shared/model/aluno.model';
import { IDiasAtendimento } from 'app/shared/model/dias-atendimento.model';
import { StatusAgenda } from 'app/shared/model/enumerations/status-agenda.model';
import { Horario } from 'app/shared/model/enumerations/horario.model';

export interface IAgendaAluno {
  id?: number;
  status?: StatusAgenda;
  horario?: Horario;
  aluno?: IAluno;
  diasAtendimento?: IDiasAtendimento;
}

export class AgendaAluno implements IAgendaAluno {
  constructor(
    public id?: number,
    public status?: StatusAgenda,
    public horario?: Horario,
    public aluno?: IAluno,
    public diasAtendimento?: IDiasAtendimento
  ) {}
}
