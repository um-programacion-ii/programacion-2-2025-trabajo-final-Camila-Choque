import { IVenta } from 'app/entities/venta/venta.model';
import { ISesion } from 'app/entities/sesion/sesion.model';

export interface IAsientos {
  id: number;
  fila?: number | null;
  columna?: number | null;
  persona?: string | null;
  estado?: string | null;
  venta?: Pick<IVenta, 'id'> | null;
  sesion?: Pick<ISesion, 'id'> | null;
}

export type NewAsientos = Omit<IAsientos, 'id'> & { id: null };
