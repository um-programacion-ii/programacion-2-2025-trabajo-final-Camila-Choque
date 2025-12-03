import { IEvento } from 'app/entities/evento/evento.model';

export interface IIntegrantes {
  id: number;
  nombre?: string | null;
  apellido?: string | null;
  identificacion?: string | null;
  evento?: Pick<IEvento, 'id'> | null;
}

export type NewIntegrantes = Omit<IIntegrantes, 'id'> & { id: null };
