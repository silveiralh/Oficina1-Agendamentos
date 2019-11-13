import { ICargo } from 'app/shared/model/cargo.model';
import { IAgendaServidor } from 'app/shared/model/agenda-servidor.model';

export interface IServidor {
  id?: number;
  codSiape?: number;
  nomeServidor?: string;
  cargo?: ICargo;
  ids?: IAgendaServidor[];
}

export class Servidor implements IServidor {
  constructor(
    public id?: number,
    public codSiape?: number,
    public nomeServidor?: string,
    public cargo?: ICargo,
    public ids?: IAgendaServidor[]
  ) {}
}
