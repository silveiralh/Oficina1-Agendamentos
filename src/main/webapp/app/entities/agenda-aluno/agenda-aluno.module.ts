import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UniagendaSharedModule } from 'app/shared/shared.module';
import { AgendaAlunoComponent } from './agenda-aluno.component';
import { AgendaAlunoDetailComponent } from './agenda-aluno-detail.component';
import { AgendaAlunoUpdateComponent } from './agenda-aluno-update.component';
import { AgendaAlunoDeletePopupComponent, AgendaAlunoDeleteDialogComponent } from './agenda-aluno-delete-dialog.component';
import { agendaAlunoRoute, agendaAlunoPopupRoute } from './agenda-aluno.route';

const ENTITY_STATES = [...agendaAlunoRoute, ...agendaAlunoPopupRoute];

@NgModule({
  imports: [UniagendaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AgendaAlunoComponent,
    AgendaAlunoDetailComponent,
    AgendaAlunoUpdateComponent,
    AgendaAlunoDeleteDialogComponent,
    AgendaAlunoDeletePopupComponent
  ],
  entryComponents: [AgendaAlunoDeleteDialogComponent]
})
export class UniagendaAgendaAlunoModule {}
