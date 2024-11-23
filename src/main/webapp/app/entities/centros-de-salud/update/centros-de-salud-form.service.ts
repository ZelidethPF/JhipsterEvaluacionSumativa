import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICentrosDeSalud, NewCentrosDeSalud } from '../centros-de-salud.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICentrosDeSalud for edit and NewCentrosDeSaludFormGroupInput for create.
 */
type CentrosDeSaludFormGroupInput = ICentrosDeSalud | PartialWithRequiredKeyOf<NewCentrosDeSalud>;

type CentrosDeSaludFormDefaults = Pick<NewCentrosDeSalud, 'id'>;

type CentrosDeSaludFormGroupContent = {
  id: FormControl<ICentrosDeSalud['id'] | NewCentrosDeSalud['id']>;
  nombre: FormControl<ICentrosDeSalud['nombre']>;
  tipo: FormControl<ICentrosDeSalud['tipo']>;
  ciudad: FormControl<ICentrosDeSalud['ciudad']>;
  direccion: FormControl<ICentrosDeSalud['direccion']>;
  telefono: FormControl<ICentrosDeSalud['telefono']>;
  email: FormControl<ICentrosDeSalud['email']>;
};

export type CentrosDeSaludFormGroup = FormGroup<CentrosDeSaludFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CentrosDeSaludFormService {
  createCentrosDeSaludFormGroup(centrosDeSalud: CentrosDeSaludFormGroupInput = { id: null }): CentrosDeSaludFormGroup {
    const centrosDeSaludRawValue = {
      ...this.getFormDefaults(),
      ...centrosDeSalud,
    };
    return new FormGroup<CentrosDeSaludFormGroupContent>({
      id: new FormControl(
        { value: centrosDeSaludRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombre: new FormControl(centrosDeSaludRawValue.nombre),
      tipo: new FormControl(centrosDeSaludRawValue.tipo),
      ciudad: new FormControl(centrosDeSaludRawValue.ciudad),
      direccion: new FormControl(centrosDeSaludRawValue.direccion),
      telefono: new FormControl(centrosDeSaludRawValue.telefono),
      email: new FormControl(centrosDeSaludRawValue.email),
    });
  }

  getCentrosDeSalud(form: CentrosDeSaludFormGroup): ICentrosDeSalud | NewCentrosDeSalud {
    return form.getRawValue() as ICentrosDeSalud | NewCentrosDeSalud;
  }

  resetForm(form: CentrosDeSaludFormGroup, centrosDeSalud: CentrosDeSaludFormGroupInput): void {
    const centrosDeSaludRawValue = { ...this.getFormDefaults(), ...centrosDeSalud };
    form.reset(
      {
        ...centrosDeSaludRawValue,
        id: { value: centrosDeSaludRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CentrosDeSaludFormDefaults {
    return {
      id: null,
    };
  }
}
