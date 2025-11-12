import dayjs from 'dayjs/esm';

import { IEvento, NewEvento } from './evento.model';

export const sampleWithRequiredData: IEvento = {
  id: 21654,
};

export const sampleWithPartialData: IEvento = {
  id: 24829,
  titulo: 'masculinize cruelly',
  resumen: 'cultivated who',
  fecha: dayjs('2025-11-12'),
  direccion: 'whirlwind',
  imagen: 'meanwhile fishery absolve',
  filaAsientos: 23901,
  columnaAsientos: 31098,
  eventoTipoNombre: 'an unblinking',
  eventoTipoDescripcion: 'faithfully tenderly duh',
  ultimaActualizacion: dayjs('2025-11-12'),
};

export const sampleWithFullData: IEvento = {
  id: 26545,
  titulo: 'outlaw upon',
  resumen: 'duh labourer pleasure',
  descripcion: 'scented unless of',
  fecha: dayjs('2025-11-12'),
  direccion: 'inquisitively amid',
  imagen: 'that sticker',
  filaAsientos: 14401,
  columnaAsientos: 6215,
  precioEntrada: 27503.17,
  eventoTipoNombre: 'extricate silently',
  eventoTipoDescripcion: 'clueless fooey',
  estado: 'unaccountably lean duh',
  ultimaActualizacion: dayjs('2025-11-11'),
};

export const sampleWithNewData: NewEvento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
