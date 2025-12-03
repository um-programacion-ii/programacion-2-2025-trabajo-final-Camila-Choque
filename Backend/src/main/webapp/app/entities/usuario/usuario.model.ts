import dayjs from 'dayjs/esm';

export interface IUsuario {
  id: number;
  username?: string | null;
  password?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  nombreAlumno?: string | null;
  descripcionProyecto?: string | null;
  jwtToken?: string | null;
  fechaRegistro?: dayjs.Dayjs | null;
  name?: string | null;
}

export type NewUsuario = Omit<IUsuario, 'id'> & { id: null };
