import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IAgendamiento } from '../agendamiento.model';

@Component({
  standalone: true,
  selector: 'jhi-agendamiento-detail',
  templateUrl: './agendamiento-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AgendamientoDetailComponent {
  agendamiento = input<IAgendamiento | null>(null);

  previousState(): void {
    window.history.back();
  }
}
