import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IIntegrantes } from '../integrantes.model';

@Component({
  selector: 'jhi-integrantes-detail',
  templateUrl: './integrantes-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class IntegrantesDetailComponent {
  integrantes = input<IIntegrantes | null>(null);

  previousState(): void {
    window.history.back();
  }
}
