import dayjs from 'dayjs/esm';

import { ISesion, NewSesion } from './sesion.model';

export const sampleWithRequiredData: ISesion = {
  id: 23407,
};

export const sampleWithPartialData: ISesion = {
  id: 3364,
  token: 'minus',
  ultimaActividad: dayjs('2025-11-11'),
};

export const sampleWithFullData: ISesion = {
  id: 30367,
  token: 'sonnet plain tame',
  estadoFlujo: 'near loyally',
  eventoSeleccionado: 29728,
  ultimaActividad: dayjs('2025-11-12'),
  expiraEn: dayjs('2025-11-12'),
};

export const sampleWithNewData: NewSesion = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
