import { IPaciente, NewPaciente } from './paciente.model';

export const sampleWithRequiredData: IPaciente = {
  id: 13862,
};

export const sampleWithPartialData: IPaciente = {
  id: 32552,
  edad: 17657,
  direccion: 'bin eek',
  ciudad: 'unimpressively mispronounce',
  email: 'Claudia58@hotmail.com',
};

export const sampleWithFullData: IPaciente = {
  id: 17098,
  nombre: 'dearly except pish',
  rut: 'probe airport wherever',
  edad: 30360,
  sexo: 'newsprint',
  direccion: 'vulgarise',
  ciudad: 'nearly gorgeous',
  telefono: 'sneaky',
  email: 'Patricia.BritoMunoz40@hotmail.com',
};

export const sampleWithNewData: NewPaciente = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
