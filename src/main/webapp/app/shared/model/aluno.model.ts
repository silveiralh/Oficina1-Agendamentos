import { IAgendaAtendimentoServidor } from 'app/shared/model/agenda-atendimento-servidor.model';
import { IAgendaAluno } from 'app/shared/model/agenda-aluno.model';
import { IAgendaReservaSala } from 'app/shared/model/agenda-reserva-sala.model';
import { Curso } from 'app/shared/model/enumerations/curso.model';

export interface IAluno {
  id?: number;
  raAluno?: number;
  nomeAluno?: string;
  periodo?: number;
  curso?: Curso;
  raAlunos?: IAgendaAtendimentoServidor[];
  raAlunos?: IAgendaAluno[];
  raAlunos?: IAgendaReservaSala[];
}

export class Aluno implements IAluno {
  constructor(
    public id?: number,
    public raAluno?: number,
    public nomeAluno?: string,
    public periodo?: number,
    public curso?: Curso,
    public raAlunos?: IAgendaAtendimentoServidor[],
    public raAlunos?: IAgendaAluno[],
    public raAlunos?: IAgendaReservaSala[]
  ) {}
}
