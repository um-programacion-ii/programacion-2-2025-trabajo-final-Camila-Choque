import { IAsientos, NewAsientos } from './asientos.model';

export const sampleWithRequiredData: IAsientos = {
  id: 4176,
};

export const sampleWithPartialData: IAsientos = {
  id: 30001,
  columna: 6686,
};

export const sampleWithFullData: IAsientos = {
  id: 14059,
  fila: 31034,
  columna: 27497,
  persona: 'gee flimsy sleepily',
  estado: 'scruple yak',
};

export const sampleWithNewData: NewAsientos = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
