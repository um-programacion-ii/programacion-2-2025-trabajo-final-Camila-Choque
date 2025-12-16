import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IAsientos, NewAsientos } from '../asientos.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAsientos for edit and NewAsientosFormGroupInput for create.
 */
type AsientosFormGroupInput = IAsientos | PartialWithRequiredKeyOf<NewAsientos>;

type AsientosFormDefaults = Pick<NewAsientos, 'id'>;

type AsientosFormGroupContent = {
  id: FormControl<IAsientos['id'] | NewAsientos['id']>;
  fila: FormControl<IAsientos['fila']>;
  columna: FormControl<IAsientos['columna']>;
  persona: FormControl<IAsientos['persona']>;
  estado: FormControl<IAsientos['estado']>;
  evento: FormControl<IAsientos['evento']>;
  venta: FormControl<IAsientos['venta']>;
  sesion: FormControl<IAsientos['sesion']>;
};

export type AsientosFormGroup = FormGroup<AsientosFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AsientosFormService {
  createAsientosFormGroup(asientos: AsientosFormGroupInput = { id: null }): AsientosFormGroup {
    const asientosRawValue = {
      ...this.getFormDefaults(),
      ...asientos,
    };
    return new FormGroup<AsientosFormGroupContent>({
      id: new FormControl(
        { value: asientosRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fila: new FormControl(asientosRawValue.fila),
      columna: new FormControl(asientosRawValue.columna),
      persona: new FormControl(asientosRawValue.persona),
      estado: new FormControl(asientosRawValue.estado),
      evento: new FormControl(asientosRawValue.evento),
      venta: new FormControl(asientosRawValue.venta),
      sesion: new FormControl(asientosRawValue.sesion),
    });
  }

  getAsientos(form: AsientosFormGroup): IAsientos | NewAsientos {
    return form.getRawValue() as IAsientos | NewAsientos;
  }

  resetForm(form: AsientosFormGroup, asientos: AsientosFormGroupInput): void {
    const asientosRawValue = { ...this.getFormDefaults(), ...asientos };
    form.reset(
      {
        ...asientosRawValue,
        id: { value: asientosRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AsientosFormDefaults {
    return {
      id: null,
    };
  }
}
