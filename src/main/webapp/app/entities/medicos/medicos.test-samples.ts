import { IMedicos, NewMedicos } from './medicos.model';

export const sampleWithRequiredData: IMedicos = {
  id: 1745,
};

export const sampleWithPartialData: IMedicos = {
  id: 14873,
  nombre: 'inhibit ugh equate',
  rut: 'stake',
  especialidad: 'brr',
  email: 'Magdalena.ArmasGrijalva99@yahoo.com',
};

export const sampleWithFullData: IMedicos = {
  id: 1025,
  nombre: 'give timely discourse',
  rut: 'but',
  especialidad: 'without',
  telefono: 'yuck pasta',
  ciudad: 'dental save',
  email: 'Santiago.CepedaLeiva@gmail.com',
  estado: false,
};

export const sampleWithNewData: NewMedicos = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
