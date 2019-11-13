import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AgendaSharedModule } from 'app/shared/shared.module';
import { AgendaAtendimentoServidorComponent } from './agenda-atendimento-servidor.component';
import { AgendaAtendimentoServidorDetailComponent } from './agenda-atendimento-servidor-detail.component';
import { AgendaAtendimentoServidorUpdateComponent } from './agenda-atendimento-servidor-update.component';
import {
  AgendaAtendimentoServidorDeletePopupComponent,
  AgendaAtendimentoServidorDeleteDialogComponent
} from './agenda-atendimento-servidor-delete-dialog.component';
import { agendaAtendimentoServidorRoute, agendaAtendimentoServidorPopupRoute } from './agenda-atendimento-servidor.route';

const ENTITY_STATES = [...agendaAtendimentoServidorRoute, ...agendaAtendimentoServidorPopupRoute];

@NgModule({
  imports: [AgendaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AgendaAtendimentoServidorComponent,
    AgendaAtendimentoServidorDetailComponent,
    AgendaAtendimentoServidorUpdateComponent,
    AgendaAtendimentoServidorDeleteDialogComponent,
    AgendaAtendimentoServidorDeletePopupComponent
  ],
  entryComponents: [AgendaAtendimentoServidorDeleteDialogComponent]
})
export class AgendaAgendaAtendimentoServidorModule {}
