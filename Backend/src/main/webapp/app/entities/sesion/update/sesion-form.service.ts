import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ISesion, NewSesion } from '../sesion.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISesion for edit and NewSesionFormGroupInput for create.
 */
type SesionFormGroupInput = ISesion | PartialWithRequiredKeyOf<NewSesion>;

type SesionFormDefaults = Pick<NewSesion, 'id'>;

type SesionFormGroupContent = {
  id: FormControl<ISesion['id'] | NewSesion['id']>;
  token: FormControl<ISesion['token']>;
  estadoFlujo: FormControl<ISesion['estadoFlujo']>;
  eventoSeleccionado: FormControl<ISesion['eventoSeleccionado']>;
  ultimaActividad: FormControl<ISesion['ultimaActividad']>;
  expiraEn: FormControl<ISesion['expiraEn']>;
};

export type SesionFormGroup = FormGroup<SesionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SesionFormService {
  createSesionFormGroup(sesion: SesionFormGroupInput = { id: null }): SesionFormGroup {
    const sesionRawValue = {
      ...this.getFormDefaults(),
      ...sesion,
    };
    return new FormGroup<SesionFormGroupContent>({
      id: new FormControl(
        { value: sesionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      token: new FormControl(sesionRawValue.token),
      estadoFlujo: new FormControl(sesionRawValue.estadoFlujo),
      eventoSeleccionado: new FormControl(sesionRawValue.eventoSeleccionado),
      ultimaActividad: new FormControl(sesionRawValue.ultimaActividad),
      expiraEn: new FormControl(sesionRawValue.expiraEn),
    });
  }

  getSesion(form: SesionFormGroup): ISesion | NewSesion {
    return form.getRawValue() as ISesion | NewSesion;
  }

  resetForm(form: SesionFormGroup, sesion: SesionFormGroupInput): void {
    const sesionRawValue = { ...this.getFormDefaults(), ...sesion };
    form.reset(
      {
        ...sesionRawValue,
        id: { value: sesionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SesionFormDefaults {
    return {
      id: null,
    };
  }
}
