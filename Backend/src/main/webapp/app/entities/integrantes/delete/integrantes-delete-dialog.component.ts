import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IIntegrantes } from '../integrantes.model';
import { IntegrantesService } from '../service/integrantes.service';

@Component({
  templateUrl: './integrantes-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class IntegrantesDeleteDialogComponent {
  integrantes?: IIntegrantes;

  protected integrantesService = inject(IntegrantesService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.integrantesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
