export interface IAsientos {
  id: number;
  fila?: number | null;
  columna?: number | null;
  persona?: string | null;
  estado?: string | null;
}

export type NewAsientos = Omit<IAsientos, 'id'> & { id: null };
