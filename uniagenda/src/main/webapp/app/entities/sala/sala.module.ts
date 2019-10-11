import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UniAgendaSharedModule } from 'app/shared/shared.module';
import { SalaComponent } from './sala.component';
import { SalaDetailComponent } from './sala-detail.component';
import { SalaUpdateComponent } from './sala-update.component';
import { SalaDeletePopupComponent, SalaDeleteDialogComponent } from './sala-delete-dialog.component';
import { salaRoute, salaPopupRoute } from './sala.route';

const ENTITY_STATES = [...salaRoute, ...salaPopupRoute];

@NgModule({
  imports: [UniAgendaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [SalaComponent, SalaDetailComponent, SalaUpdateComponent, SalaDeleteDialogComponent, SalaDeletePopupComponent],
  entryComponents: [SalaDeleteDialogComponent]
})
export class UniAgendaSalaModule {}
