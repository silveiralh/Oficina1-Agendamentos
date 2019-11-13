import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AgendaSharedModule } from 'app/shared/shared.module';
import { AgendaSalaComponent } from './agenda-sala.component';
import { AgendaSalaDetailComponent } from './agenda-sala-detail.component';
import { AgendaSalaUpdateComponent } from './agenda-sala-update.component';
import { AgendaSalaDeletePopupComponent, AgendaSalaDeleteDialogComponent } from './agenda-sala-delete-dialog.component';
import { agendaSalaRoute, agendaSalaPopupRoute } from './agenda-sala.route';

const ENTITY_STATES = [...agendaSalaRoute, ...agendaSalaPopupRoute];

@NgModule({
  imports: [AgendaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AgendaSalaComponent,
    AgendaSalaDetailComponent,
    AgendaSalaUpdateComponent,
    AgendaSalaDeleteDialogComponent,
    AgendaSalaDeletePopupComponent
  ],
  entryComponents: [AgendaSalaDeleteDialogComponent]
})
export class AgendaAgendaSalaModule {}
