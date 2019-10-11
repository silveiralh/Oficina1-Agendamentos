import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UniAgendaSharedModule } from 'app/shared/shared.module';
import { ServidorComponent } from './servidor.component';
import { ServidorDetailComponent } from './servidor-detail.component';
import { ServidorUpdateComponent } from './servidor-update.component';
import { ServidorDeletePopupComponent, ServidorDeleteDialogComponent } from './servidor-delete-dialog.component';
import { servidorRoute, servidorPopupRoute } from './servidor.route';

const ENTITY_STATES = [...servidorRoute, ...servidorPopupRoute];

@NgModule({
  imports: [UniAgendaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServidorComponent,
    ServidorDetailComponent,
    ServidorUpdateComponent,
    ServidorDeleteDialogComponent,
    ServidorDeletePopupComponent
  ],
  entryComponents: [ServidorDeleteDialogComponent]
})
export class UniAgendaServidorModule {}
