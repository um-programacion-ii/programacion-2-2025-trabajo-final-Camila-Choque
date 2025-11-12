import dayjs from 'dayjs/esm';

import { IUsuario, NewUsuario } from './usuario.model';

export const sampleWithRequiredData: IUsuario = {
  id: 23732,
};

export const sampleWithPartialData: IUsuario = {
  id: 13582,
  password: 'bravely',
  lastName: 'Jurado Zambrano',
  nombreAlumno: 'grizzled',
  jwtToken: 'taxicab shrilly',
  fechaRegistro: dayjs('2025-11-12'),
};

export const sampleWithFullData: IUsuario = {
  id: 32608,
  username: 'outlaw lest',
  password: 'nocturnal yum up',
  firstName: 'Catalina',
  lastName: 'Fonseca Zaragoza',
  email: 'Juan_PolancoRobles59@hotmail.com',
  nombreAlumno: 'thoroughly',
  descripcionProyecto: 'quizzically trusting',
  jwtToken: 'than atop before',
  fechaRegistro: dayjs('2025-11-12'),
  name: 'jaggedly unto scamper',
};

export const sampleWithNewData: NewUsuario = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
