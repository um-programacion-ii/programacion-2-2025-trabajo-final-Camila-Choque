import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatePipe } from 'app/shared/date';
import { ISesion } from '../sesion.model';

@Component({
  selector: 'jhi-sesion-detail',
  templateUrl: './sesion-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatePipe],
})
export class SesionDetailComponent {
  sesion = input<ISesion | null>(null);

  previousState(): void {
    window.history.back();
  }
}
