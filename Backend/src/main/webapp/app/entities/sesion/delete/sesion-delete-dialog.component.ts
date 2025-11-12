import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISesion } from '../sesion.model';
import { SesionService } from '../service/sesion.service';

@Component({
  templateUrl: './sesion-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SesionDeleteDialogComponent {
  sesion?: ISesion;

  protected sesionService = inject(SesionService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sesionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
