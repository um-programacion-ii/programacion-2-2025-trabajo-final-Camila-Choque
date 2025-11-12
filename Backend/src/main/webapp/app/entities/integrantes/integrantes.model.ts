export interface IIntegrantes {
  id: number;
  nombre?: string | null;
  apellido?: string | null;
  identificacion?: string | null;
}

export type NewIntegrantes = Omit<IIntegrantes, 'id'> & { id: null };
