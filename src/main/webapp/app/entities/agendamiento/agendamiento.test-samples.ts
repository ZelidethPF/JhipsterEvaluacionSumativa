import dayjs from 'dayjs/esm';

import { IAgendamiento, NewAgendamiento } from './agendamiento.model';

export const sampleWithRequiredData: IAgendamiento = {
  id: 6931,
};

export const sampleWithPartialData: IAgendamiento = {
  id: 3469,
  paciente: 'ugh mid',
  medico: 'popularity steeple potentially',
  centro: 'phew oh midst',
  fechahora: dayjs('2024-11-22T11:10'),
  estado: 'meh',
  origen: 'wound judgementally pastel',
  motivo: 'as strictly',
  fechaingreso: dayjs('2024-11-23'),
  nivelprioridad: 'pertinent',
};

export const sampleWithFullData: IAgendamiento = {
  id: 7013,
  paciente: 'instead',
  medico: 'besides',
  centro: 'scarcely slimy amid',
  fechahora: dayjs('2024-11-22T18:11'),
  estado: 'after source restfully',
  origen: 'noon',
  motivo: 'longingly bah following',
  fechaingreso: dayjs('2024-11-22'),
  nivelprioridad: 'along why',
};

export const sampleWithNewData: NewAgendamiento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
