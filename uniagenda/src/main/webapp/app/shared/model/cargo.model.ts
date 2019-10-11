import { IServidor } from 'app/shared/model/servidor.model';

export interface ICargo {
  id?: number;
  nomeCargo?: string;
  servidor?: IServidor;
}

export class Cargo implements ICargo {
  constructor(public id?: number, public nomeCargo?: string, public servidor?: IServidor) {}
}
