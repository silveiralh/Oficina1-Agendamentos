import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AgendaSharedModule } from 'app/shared/shared.module';
import { AlunoComponent } from './aluno.component';
import { AlunoDetailComponent } from './aluno-detail.component';
import { AlunoUpdateComponent } from './aluno-update.component';
import { AlunoDeletePopupComponent, AlunoDeleteDialogComponent } from './aluno-delete-dialog.component';
import { alunoRoute, alunoPopupRoute } from './aluno.route';

const ENTITY_STATES = [...alunoRoute, ...alunoPopupRoute];

@NgModule({
  imports: [AgendaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [AlunoComponent, AlunoDetailComponent, AlunoUpdateComponent, AlunoDeleteDialogComponent, AlunoDeletePopupComponent],
  entryComponents: [AlunoDeleteDialogComponent]
})
export class AgendaAlunoModule {}
