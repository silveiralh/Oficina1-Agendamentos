import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cargo',
        loadChildren: () => import('./cargo/cargo.module').then(m => m.UniagendaCargoModule)
      },
      {
        path: 'sala',
        loadChildren: () => import('./sala/sala.module').then(m => m.UniagendaSalaModule)
      },
      {
        path: 'servidor',
        loadChildren: () => import('./servidor/servidor.module').then(m => m.UniagendaServidorModule)
      },
      {
        path: 'agenda-atendimento-servidor',
        loadChildren: () =>
          import('./agenda-atendimento-servidor/agenda-atendimento-servidor.module').then(m => m.UniagendaAgendaAtendimentoServidorModule)
      },
      {
        path: 'aluno',
        loadChildren: () => import('./aluno/aluno.module').then(m => m.UniagendaAlunoModule)
      },
      {
        path: 'agenda-servidor',
        loadChildren: () => import('./agenda-servidor/agenda-servidor.module').then(m => m.UniagendaAgendaServidorModule)
      },
      {
        path: 'agenda-aluno',
        loadChildren: () => import('./agenda-aluno/agenda-aluno.module').then(m => m.UniagendaAgendaAlunoModule)
      },
      {
        path: 'agenda-sala',
        loadChildren: () => import('./agenda-sala/agenda-sala.module').then(m => m.UniagendaAgendaSalaModule)
      },
      {
        path: 'agenda-reserva-sala',
        loadChildren: () => import('./agenda-reserva-sala/agenda-reserva-sala.module').then(m => m.UniagendaAgendaReservaSalaModule)
      },
      {
        path: 'dias-atendimento',
        loadChildren: () => import('./dias-atendimento/dias-atendimento.module').then(m => m.UniagendaDiasAtendimentoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class UniagendaEntityModule {}
