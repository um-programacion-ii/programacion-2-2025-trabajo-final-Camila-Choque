import dayjs from 'dayjs/esm';

import { IVenta, NewVenta } from './venta.model';

export const sampleWithRequiredData: IVenta = {
  id: 21245,
};

export const sampleWithPartialData: IVenta = {
  id: 24242,
  ventaIdCatedra: 2505,
  resultado: false,
  descripcion: 'daily',
  cantidadAsientos: 25523,
  estado: 'hawk who',
};

export const sampleWithFullData: IVenta = {
  id: 8723,
  ventaIdCatedra: 26179,
  fechaVenta: dayjs('2025-11-12'),
  precioVenta: 19619.07,
  resultado: true,
  descripcion: 'lest good-natured',
  cantidadAsientos: 29395,
  estado: 'that over negligible',
};

export const sampleWithNewData: NewVenta = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
