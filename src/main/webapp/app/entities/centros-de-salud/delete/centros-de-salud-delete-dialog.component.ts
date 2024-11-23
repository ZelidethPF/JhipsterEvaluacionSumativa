import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICentrosDeSalud } from '../centros-de-salud.model';
import { CentrosDeSaludService } from '../service/centros-de-salud.service';

@Component({
  standalone: true,
  templateUrl: './centros-de-salud-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CentrosDeSaludDeleteDialogComponent {
  centrosDeSalud?: ICentrosDeSalud;

  protected centrosDeSaludService = inject(CentrosDeSaludService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.centrosDeSaludService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
