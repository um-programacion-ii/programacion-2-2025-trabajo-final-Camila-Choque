import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IUsuario, NewUsuario } from '../usuario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUsuario for edit and NewUsuarioFormGroupInput for create.
 */
type UsuarioFormGroupInput = IUsuario | PartialWithRequiredKeyOf<NewUsuario>;

type UsuarioFormDefaults = Pick<NewUsuario, 'id'>;

type UsuarioFormGroupContent = {
  id: FormControl<IUsuario['id'] | NewUsuario['id']>;
  username: FormControl<IUsuario['username']>;
  password: FormControl<IUsuario['password']>;
  firstName: FormControl<IUsuario['firstName']>;
  lastName: FormControl<IUsuario['lastName']>;
  email: FormControl<IUsuario['email']>;
  nombreAlumno: FormControl<IUsuario['nombreAlumno']>;
  descripcionProyecto: FormControl<IUsuario['descripcionProyecto']>;
  jwtToken: FormControl<IUsuario['jwtToken']>;
  fechaRegistro: FormControl<IUsuario['fechaRegistro']>;
  name: FormControl<IUsuario['name']>;
};

export type UsuarioFormGroup = FormGroup<UsuarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UsuarioFormService {
  createUsuarioFormGroup(usuario: UsuarioFormGroupInput = { id: null }): UsuarioFormGroup {
    const usuarioRawValue = {
      ...this.getFormDefaults(),
      ...usuario,
    };
    return new FormGroup<UsuarioFormGroupContent>({
      id: new FormControl(
        { value: usuarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      username: new FormControl(usuarioRawValue.username),
      password: new FormControl(usuarioRawValue.password),
      firstName: new FormControl(usuarioRawValue.firstName),
      lastName: new FormControl(usuarioRawValue.lastName),
      email: new FormControl(usuarioRawValue.email),
      nombreAlumno: new FormControl(usuarioRawValue.nombreAlumno),
      descripcionProyecto: new FormControl(usuarioRawValue.descripcionProyecto),
      jwtToken: new FormControl(usuarioRawValue.jwtToken),
      fechaRegistro: new FormControl(usuarioRawValue.fechaRegistro),
      name: new FormControl(usuarioRawValue.name),
    });
  }

  getUsuario(form: UsuarioFormGroup): IUsuario | NewUsuario {
    return form.getRawValue() as IUsuario | NewUsuario;
  }

  resetForm(form: UsuarioFormGroup, usuario: UsuarioFormGroupInput): void {
    const usuarioRawValue = { ...this.getFormDefaults(), ...usuario };
    form.reset(
      {
        ...usuarioRawValue,
        id: { value: usuarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): UsuarioFormDefaults {
    return {
      id: null,
    };
  }
}
