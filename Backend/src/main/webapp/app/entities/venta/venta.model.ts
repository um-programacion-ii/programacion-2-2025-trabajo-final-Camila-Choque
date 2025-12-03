import dayjs from 'dayjs/esm';

export interface IVenta {
  id: number;
  ventaIdCatedra?: number | null;
  fechaVenta?: dayjs.Dayjs | null;
  precioVenta?: number | null;
  resultado?: boolean | null;
  descripcion?: string | null;
  cantidadAsientos?: number | null;
  estado?: string | null;
}

export type NewVenta = Omit<IVenta, 'id'> & { id: null };
