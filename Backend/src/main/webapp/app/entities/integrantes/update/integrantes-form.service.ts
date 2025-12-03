import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IIntegrantes, NewIntegrantes } from '../integrantes.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIntegrantes for edit and NewIntegrantesFormGroupInput for create.
 */
type IntegrantesFormGroupInput = IIntegrantes | PartialWithRequiredKeyOf<NewIntegrantes>;

type IntegrantesFormDefaults = Pick<NewIntegrantes, 'id'>;

type IntegrantesFormGroupContent = {
  id: FormControl<IIntegrantes['id'] | NewIntegrantes['id']>;
  nombre: FormControl<IIntegrantes['nombre']>;
  apellido: FormControl<IIntegrantes['apellido']>;
  identificacion: FormControl<IIntegrantes['identificacion']>;
  evento: FormControl<IIntegrantes['evento']>;
};

export type IntegrantesFormGroup = FormGroup<IntegrantesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IntegrantesFormService {
  createIntegrantesFormGroup(integrantes: IntegrantesFormGroupInput = { id: null }): IntegrantesFormGroup {
    const integrantesRawValue = {
      ...this.getFormDefaults(),
      ...integrantes,
    };
    return new FormGroup<IntegrantesFormGroupContent>({
      id: new FormControl(
        { value: integrantesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombre: new FormControl(integrantesRawValue.nombre),
      apellido: new FormControl(integrantesRawValue.apellido),
      identificacion: new FormControl(integrantesRawValue.identificacion),
      evento: new FormControl(integrantesRawValue.evento),
    });
  }

  getIntegrantes(form: IntegrantesFormGroup): IIntegrantes | NewIntegrantes {
    return form.getRawValue() as IIntegrantes | NewIntegrantes;
  }

  resetForm(form: IntegrantesFormGroup, integrantes: IntegrantesFormGroupInput): void {
    const integrantesRawValue = { ...this.getFormDefaults(), ...integrantes };
    form.reset(
      {
        ...integrantesRawValue,
        id: { value: integrantesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): IntegrantesFormDefaults {
    return {
      id: null,
    };
  }
}
