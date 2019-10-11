import { ICargo } from 'app/shared/model/cargo.model';
import { IAgendaServidor } from 'app/shared/model/agenda-servidor.model';
import { IDiasAtendimento } from 'app/shared/model/dias-atendimento.model';

export interface IServidor {
  id?: number;
  codSiape?: number;
  nomeServidor?: string;
  nomeCargos?: ICargo[];
  agendaServidor?: IAgendaServidor;
  diasAtendimento?: IDiasAtendimento;
}

export class Servidor implements IServidor {
  constructor(
    public id?: number,
    public codSiape?: number,
    public nomeServidor?: string,
    public nomeCargos?: ICargo[],
    public agendaServidor?: IAgendaServidor,
    public diasAtendimento?: IDiasAtendimento
  ) {}
}
