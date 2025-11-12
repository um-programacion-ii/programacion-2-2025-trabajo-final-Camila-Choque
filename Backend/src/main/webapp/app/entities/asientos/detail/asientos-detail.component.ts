import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IAsientos } from '../asientos.model';

@Component({
  selector: 'jhi-asientos-detail',
  templateUrl: './asientos-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class AsientosDetailComponent {
  asientos = input<IAsientos | null>(null);

  previousState(): void {
    window.history.back();
  }
}
