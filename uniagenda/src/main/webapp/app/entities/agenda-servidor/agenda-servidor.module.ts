import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UniAgendaSharedModule } from 'app/shared/shared.module';
import { AgendaServidorComponent } from './agenda-servidor.component';
import { AgendaServidorDetailComponent } from './agenda-servidor-detail.component';
import { AgendaServidorUpdateComponent } from './agenda-servidor-update.component';
import { AgendaServidorDeletePopupComponent, AgendaServidorDeleteDialogComponent } from './agenda-servidor-delete-dialog.component';
import { agendaServidorRoute, agendaServidorPopupRoute } from './agenda-servidor.route';

const ENTITY_STATES = [...agendaServidorRoute, ...agendaServidorPopupRoute];

@NgModule({
  imports: [UniAgendaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AgendaServidorComponent,
    AgendaServidorDetailComponent,
    AgendaServidorUpdateComponent,
    AgendaServidorDeleteDialogComponent,
    AgendaServidorDeletePopupComponent
  ],
  entryComponents: [AgendaServidorDeleteDialogComponent]
})
export class UniAgendaAgendaServidorModule {}
