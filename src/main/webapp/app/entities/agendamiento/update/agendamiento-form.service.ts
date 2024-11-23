import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAgendamiento, NewAgendamiento } from '../agendamiento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAgendamiento for edit and NewAgendamientoFormGroupInput for create.
 */
type AgendamientoFormGroupInput = IAgendamiento | PartialWithRequiredKeyOf<NewAgendamiento>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAgendamiento | NewAgendamiento> = Omit<T, 'fechahora'> & {
  fechahora?: string | null;
};

type AgendamientoFormRawValue = FormValueOf<IAgendamiento>;

type NewAgendamientoFormRawValue = FormValueOf<NewAgendamiento>;

type AgendamientoFormDefaults = Pick<NewAgendamiento, 'id' | 'fechahora'>;

type AgendamientoFormGroupContent = {
  id: FormControl<AgendamientoFormRawValue['id'] | NewAgendamiento['id']>;
  paciente: FormControl<AgendamientoFormRawValue['paciente']>;
  medico: FormControl<AgendamientoFormRawValue['medico']>;
  centro: FormControl<AgendamientoFormRawValue['centro']>;
  fechahora: FormControl<AgendamientoFormRawValue['fechahora']>;
  estado: FormControl<AgendamientoFormRawValue['estado']>;
  origen: FormControl<AgendamientoFormRawValue['origen']>;
  motivo: FormControl<AgendamientoFormRawValue['motivo']>;
  fechaingreso: FormControl<AgendamientoFormRawValue['fechaingreso']>;
  nivelprioridad: FormControl<AgendamientoFormRawValue['nivelprioridad']>;
};

export type AgendamientoFormGroup = FormGroup<AgendamientoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AgendamientoFormService {
  createAgendamientoFormGroup(agendamiento: AgendamientoFormGroupInput = { id: null }): AgendamientoFormGroup {
    const agendamientoRawValue = this.convertAgendamientoToAgendamientoRawValue({
      ...this.getFormDefaults(),
      ...agendamiento,
    });
    return new FormGroup<AgendamientoFormGroupContent>({
      id: new FormControl(
        { value: agendamientoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      paciente: new FormControl(agendamientoRawValue.paciente),
      medico: new FormControl(agendamientoRawValue.medico),
      centro: new FormControl(agendamientoRawValue.centro),
      fechahora: new FormControl(agendamientoRawValue.fechahora),
      estado: new FormControl(agendamientoRawValue.estado),
      origen: new FormControl(agendamientoRawValue.origen),
      motivo: new FormControl(agendamientoRawValue.motivo),
      fechaingreso: new FormControl(agendamientoRawValue.fechaingreso),
      nivelprioridad: new FormControl(agendamientoRawValue.nivelprioridad),
    });
  }

  getAgendamiento(form: AgendamientoFormGroup): IAgendamiento | NewAgendamiento {
    return this.convertAgendamientoRawValueToAgendamiento(form.getRawValue() as AgendamientoFormRawValue | NewAgendamientoFormRawValue);
  }

  resetForm(form: AgendamientoFormGroup, agendamiento: AgendamientoFormGroupInput): void {
    const agendamientoRawValue = this.convertAgendamientoToAgendamientoRawValue({ ...this.getFormDefaults(), ...agendamiento });
    form.reset(
      {
        ...agendamientoRawValue,
        id: { value: agendamientoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AgendamientoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      fechahora: currentTime,
    };
  }

  private convertAgendamientoRawValueToAgendamiento(
    rawAgendamiento: AgendamientoFormRawValue | NewAgendamientoFormRawValue,
  ): IAgendamiento | NewAgendamiento {
    return {
      ...rawAgendamiento,
      fechahora: dayjs(rawAgendamiento.fechahora, DATE_TIME_FORMAT),
    };
  }

  private convertAgendamientoToAgendamientoRawValue(
    agendamiento: IAgendamiento | (Partial<NewAgendamiento> & AgendamientoFormDefaults),
  ): AgendamientoFormRawValue | PartialWithRequiredKeyOf<NewAgendamientoFormRawValue> {
    return {
      ...agendamiento,
      fechahora: agendamiento.fechahora ? agendamiento.fechahora.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
