import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ICentrosDeSalud } from '../centros-de-salud.model';

@Component({
  standalone: true,
  selector: 'jhi-centros-de-salud-detail',
  templateUrl: './centros-de-salud-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CentrosDeSaludDetailComponent {
  centrosDeSalud = input<ICentrosDeSalud | null>(null);

  previousState(): void {
    window.history.back();
  }
}
