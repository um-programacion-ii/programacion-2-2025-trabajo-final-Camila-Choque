import dayjs from 'dayjs/esm';

export interface ISesion {
  id: number;
  token?: string | null;
  estadoFlujo?: string | null;
  eventoSeleccionado?: number | null;
  ultimaActividad?: dayjs.Dayjs | null;
  expiraEn?: dayjs.Dayjs | null;
}

export type NewSesion = Omit<ISesion, 'id'> & { id: null };
