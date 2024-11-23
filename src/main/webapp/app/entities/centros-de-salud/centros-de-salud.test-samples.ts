import { ICentrosDeSalud, NewCentrosDeSalud } from './centros-de-salud.model';

export const sampleWithRequiredData: ICentrosDeSalud = {
  id: 9882,
};

export const sampleWithPartialData: ICentrosDeSalud = {
  id: 7834,
  tipo: 'comestible furthermore',
  ciudad: 'what emphasize',
  telefono: 'oof',
  email: 'Cristobal90@yahoo.com',
};

export const sampleWithFullData: ICentrosDeSalud = {
  id: 2030,
  nombre: 'thorough',
  tipo: 'strictly gosh indeed',
  ciudad: 'beloved phew',
  direccion: 'nor alliance',
  telefono: 'beside',
  email: 'Susana1@gmail.com',
};

export const sampleWithNewData: NewCentrosDeSalud = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
