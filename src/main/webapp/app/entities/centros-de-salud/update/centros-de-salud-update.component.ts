import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICentrosDeSalud } from '../centros-de-salud.model';
import { CentrosDeSaludService } from '../service/centros-de-salud.service';
import { CentrosDeSaludFormGroup, CentrosDeSaludFormService } from './centros-de-salud-form.service';

@Component({
  standalone: true,
  selector: 'jhi-centros-de-salud-update',
  templateUrl: './centros-de-salud-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CentrosDeSaludUpdateComponent implements OnInit {
  isSaving = false;
  centrosDeSalud: ICentrosDeSalud | null = null;

  protected centrosDeSaludService = inject(CentrosDeSaludService);
  protected centrosDeSaludFormService = inject(CentrosDeSaludFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CentrosDeSaludFormGroup = this.centrosDeSaludFormService.createCentrosDeSaludFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ centrosDeSalud }) => {
      this.centrosDeSalud = centrosDeSalud;
      if (centrosDeSalud) {
        this.updateForm(centrosDeSalud);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const centrosDeSalud = this.centrosDeSaludFormService.getCentrosDeSalud(this.editForm);
    if (centrosDeSalud.id !== null) {
      this.subscribeToSaveResponse(this.centrosDeSaludService.update(centrosDeSalud));
    } else {
      this.subscribeToSaveResponse(this.centrosDeSaludService.create(centrosDeSalud));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICentrosDeSalud>>): void {
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

  protected updateForm(centrosDeSalud: ICentrosDeSalud): void {
    this.centrosDeSalud = centrosDeSalud;
    this.centrosDeSaludFormService.resetForm(this.editForm, centrosDeSalud);
  }
}
