import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAsientos } from '../asientos.model';
import { AsientosService } from '../service/asientos.service';

@Component({
  templateUrl: './asientos-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AsientosDeleteDialogComponent {
  asientos?: IAsientos;

  protected asientosService = inject(AsientosService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.asientosService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
