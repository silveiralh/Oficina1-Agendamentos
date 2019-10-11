import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cargo',
        loadChildren: () => import('./cargo/cargo.module').then(m => m.UniAgendaCargoModule)
      },
      {
        path: 'sala',
        loadChildren: () => import('./sala/sala.module').then(m => m.UniAgendaSalaModule)
      },
      {
        path: 'servidor',
        loadChildren: () => import('./servidor/servidor.module').then(m => m.UniAgendaServidorModule)
      },
      {
        path: 'agenda-atendimento-servidor',
        loadChildren: () =>
          import('./agenda-atendimento-servidor/agenda-atendimento-servidor.module').then(m => m.UniAgendaAgendaAtendimentoServidorModule)
      },
      {
        path: 'aluno',
        loadChildren: () => import('./aluno/aluno.module').then(m => m.UniAgendaAlunoModule)
      },
      {
        path: 'agenda-servidor',
        loadChildren: () => import('./agenda-servidor/agenda-servidor.module').then(m => m.UniAgendaAgendaServidorModule)
      },
      {
        path: 'agenda-aluno',
        loadChildren: () => import('./agenda-aluno/agenda-aluno.module').then(m => m.UniAgendaAgendaAlunoModule)
      },
      {
        path: 'agenda-sala',
        loadChildren: () => import('./agenda-sala/agenda-sala.module').then(m => m.UniAgendaAgendaSalaModule)
      },
      {
        path: 'agenda-reserva-sala',
        loadChildren: () => import('./agenda-reserva-sala/agenda-reserva-sala.module').then(m => m.UniAgendaAgendaReservaSalaModule)
      },
      {
        path: 'dias-atendimento',
        loadChildren: () => import('./dias-atendimento/dias-atendimento.module').then(m => m.UniAgendaDiasAtendimentoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class UniAgendaEntityModule {}
