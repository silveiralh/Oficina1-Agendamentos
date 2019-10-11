import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UniAgendaSharedModule } from 'app/shared/shared.module';
import { AgendaReservaSalaComponent } from './agenda-reserva-sala.component';
import { AgendaReservaSalaDetailComponent } from './agenda-reserva-sala-detail.component';
import { AgendaReservaSalaUpdateComponent } from './agenda-reserva-sala-update.component';
import {
  AgendaReservaSalaDeletePopupComponent,
  AgendaReservaSalaDeleteDialogComponent
} from './agenda-reserva-sala-delete-dialog.component';
import { agendaReservaSalaRoute, agendaReservaSalaPopupRoute } from './agenda-reserva-sala.route';

const ENTITY_STATES = [...agendaReservaSalaRoute, ...agendaReservaSalaPopupRoute];

@NgModule({
  imports: [UniAgendaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AgendaReservaSalaComponent,
    AgendaReservaSalaDetailComponent,
    AgendaReservaSalaUpdateComponent,
    AgendaReservaSalaDeleteDialogComponent,
    AgendaReservaSalaDeletePopupComponent
  ],
  entryComponents: [AgendaReservaSalaDeleteDialogComponent]
})
export class UniAgendaAgendaReservaSalaModule {}
