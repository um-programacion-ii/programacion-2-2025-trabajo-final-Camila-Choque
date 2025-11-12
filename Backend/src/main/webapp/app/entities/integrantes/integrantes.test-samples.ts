import { IIntegrantes, NewIntegrantes } from './integrantes.model';

export const sampleWithRequiredData: IIntegrantes = {
  id: 24640,
};

export const sampleWithPartialData: IIntegrantes = {
  id: 15234,
  apellido: 'shrill below confide',
};

export const sampleWithFullData: IIntegrantes = {
  id: 27083,
  nombre: 'in',
  apellido: 'off',
  identificacion: 'phew meadow',
};

export const sampleWithNewData: NewIntegrantes = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
