import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IVenta, NewVenta } from '../venta.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVenta for edit and NewVentaFormGroupInput for create.
 */
type VentaFormGroupInput = IVenta | PartialWithRequiredKeyOf<NewVenta>;

type VentaFormDefaults = Pick<NewVenta, 'id' | 'resultado'>;

type VentaFormGroupContent = {
  id: FormControl<IVenta['id'] | NewVenta['id']>;
  ventaIdCatedra: FormControl<IVenta['ventaIdCatedra']>;
  fechaVenta: FormControl<IVenta['fechaVenta']>;
  precioVenta: FormControl<IVenta['precioVenta']>;
  resultado: FormControl<IVenta['resultado']>;
  descripcion: FormControl<IVenta['descripcion']>;
  cantidadAsientos: FormControl<IVenta['cantidadAsientos']>;
  estado: FormControl<IVenta['estado']>;
};

export type VentaFormGroup = FormGroup<VentaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VentaFormService {
  createVentaFormGroup(venta: VentaFormGroupInput = { id: null }): VentaFormGroup {
    const ventaRawValue = {
      ...this.getFormDefaults(),
      ...venta,
    };
    return new FormGroup<VentaFormGroupContent>({
      id: new FormControl(
        { value: ventaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      ventaIdCatedra: new FormControl(ventaRawValue.ventaIdCatedra),
      fechaVenta: new FormControl(ventaRawValue.fechaVenta),
      precioVenta: new FormControl(ventaRawValue.precioVenta),
      resultado: new FormControl(ventaRawValue.resultado),
      descripcion: new FormControl(ventaRawValue.descripcion),
      cantidadAsientos: new FormControl(ventaRawValue.cantidadAsientos),
      estado: new FormControl(ventaRawValue.estado),
    });
  }

  getVenta(form: VentaFormGroup): IVenta | NewVenta {
    return form.getRawValue() as IVenta | NewVenta;
  }

  resetForm(form: VentaFormGroup, venta: VentaFormGroupInput): void {
    const ventaRawValue = { ...this.getFormDefaults(), ...venta };
    form.reset(
      {
        ...ventaRawValue,
        id: { value: ventaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): VentaFormDefaults {
    return {
      id: null,
      resultado: false,
    };
  }
}
