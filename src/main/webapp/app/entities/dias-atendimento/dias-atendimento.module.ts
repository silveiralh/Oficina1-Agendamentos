import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UniagendaSharedModule } from 'app/shared/shared.module';
import { DiasAtendimentoComponent } from './dias-atendimento.component';
import { DiasAtendimentoDetailComponent } from './dias-atendimento-detail.component';
import { DiasAtendimentoUpdateComponent } from './dias-atendimento-update.component';
import { DiasAtendimentoDeletePopupComponent, DiasAtendimentoDeleteDialogComponent } from './dias-atendimento-delete-dialog.component';
import { diasAtendimentoRoute, diasAtendimentoPopupRoute } from './dias-atendimento.route';

const ENTITY_STATES = [...diasAtendimentoRoute, ...diasAtendimentoPopupRoute];

@NgModule({
  imports: [UniagendaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DiasAtendimentoComponent,
    DiasAtendimentoDetailComponent,
    DiasAtendimentoUpdateComponent,
    DiasAtendimentoDeleteDialogComponent,
    DiasAtendimentoDeletePopupComponent
  ],
  entryComponents: [DiasAtendimentoDeleteDialogComponent]
})
export class UniagendaDiasAtendimentoModule {}
