import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAgendamiento } from '../agendamiento.model';
import { AgendamientoService } from '../service/agendamiento.service';
import { AgendamientoFormGroup, AgendamientoFormService } from './agendamiento-form.service';

@Component({
  standalone: true,
  selector: 'jhi-agendamiento-update',
  templateUrl: './agendamiento-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AgendamientoUpdateComponent implements OnInit {
  isSaving = false;
  agendamiento: IAgendamiento | null = null;

  protected agendamientoService = inject(AgendamientoService);
  protected agendamientoFormService = inject(AgendamientoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AgendamientoFormGroup = this.agendamientoFormService.createAgendamientoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agendamiento }) => {
      this.agendamiento = agendamiento;
      if (agendamiento) {
        this.updateForm(agendamiento);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agendamiento = this.agendamientoFormService.getAgendamiento(this.editForm);
    if (agendamiento.id !== null) {
      this.subscribeToSaveResponse(this.agendamientoService.update(agendamiento));
    } else {
      this.subscribeToSaveResponse(this.agendamientoService.create(agendamiento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgendamiento>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(agendamiento: IAgendamiento): void {
    this.agendamiento = agendamiento;
    this.agendamientoFormService.resetForm(this.editForm, agendamiento);
  }
}
