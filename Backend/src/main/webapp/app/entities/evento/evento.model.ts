import dayjs from 'dayjs/esm';

export interface IEvento {
  id: number;
  titulo?: string | null;
  resumen?: string | null;
  descripcion?: string | null;
  fecha?: dayjs.Dayjs | null;
  direccion?: string | null;
  imagen?: string | null;
  filaAsientos?: number | null;
  columnaAsientos?: number | null;
  precioEntrada?: number | null;
  eventoTipoNombre?: string | null;
  eventoTipoDescripcion?: string | null;
  estado?: string | null;
  ultimaActualizacion?: dayjs.Dayjs | null;
}

export type NewEvento = Omit<IEvento, 'id'> & { id: null };
