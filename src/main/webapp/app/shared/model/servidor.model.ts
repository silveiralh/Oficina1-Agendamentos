import { ICargo } from 'app/shared/model/cargo.model';
import { IDiasAtendimento } from 'app/shared/model/dias-atendimento.model';
import { IAgendaServidor } from 'app/shared/model/agenda-servidor.model';

export interface IServidor {
  id?: number;
  codSiape?: number;
  nomeServidor?: string;
  cargo?: ICargo;
  codSiapes?: IDiasAtendimento[];
  codSiapes?: IAgendaServidor[];
}

export class Servidor implements IServidor {
  constructor(
    public id?: number,
    public codSiape?: number,
    public nomeServidor?: string,
    public cargo?: ICargo,
    public codSiapes?: IDiasAtendimento[],
    public codSiapes?: IAgendaServidor[]
  ) {}
}
