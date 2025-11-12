import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IEvento, NewEvento } from '../evento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEvento for edit and NewEventoFormGroupInput for create.
 */
type EventoFormGroupInput = IEvento | PartialWithRequiredKeyOf<NewEvento>;

type EventoFormDefaults = Pick<NewEvento, 'id'>;

type EventoFormGroupContent = {
  id: FormControl<IEvento['id'] | NewEvento['id']>;
  titulo: FormControl<IEvento['titulo']>;
  resumen: FormControl<IEvento['resumen']>;
  descripcion: FormControl<IEvento['descripcion']>;
  fecha: FormControl<IEvento['fecha']>;
  direccion: FormControl<IEvento['direccion']>;
  imagen: FormControl<IEvento['imagen']>;
  filaAsientos: FormControl<IEvento['filaAsientos']>;
  columnaAsientos: FormControl<IEvento['columnaAsientos']>;
  precioEntrada: FormControl<IEvento['precioEntrada']>;
  eventoTipoNombre: FormControl<IEvento['eventoTipoNombre']>;
  eventoTipoDescripcion: FormControl<IEvento['eventoTipoDescripcion']>;
  estado: FormControl<IEvento['estado']>;
  ultimaActualizacion: FormControl<IEvento['ultimaActualizacion']>;
};

export type EventoFormGroup = FormGroup<EventoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EventoFormService {
  createEventoFormGroup(evento: EventoFormGroupInput = { id: null }): EventoFormGroup {
    const eventoRawValue = {
      ...this.getFormDefaults(),
      ...evento,
    };
    return new FormGroup<EventoFormGroupContent>({
      id: new FormControl(
        { value: eventoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      titulo: new FormControl(eventoRawValue.titulo),
      resumen: new FormControl(eventoRawValue.resumen),
      descripcion: new FormControl(eventoRawValue.descripcion),
      fecha: new FormControl(eventoRawValue.fecha),
      direccion: new FormControl(eventoRawValue.direccion),
      imagen: new FormControl(eventoRawValue.imagen),
      filaAsientos: new FormControl(eventoRawValue.filaAsientos),
      columnaAsientos: new FormControl(eventoRawValue.columnaAsientos),
      precioEntrada: new FormControl(eventoRawValue.precioEntrada),
      eventoTipoNombre: new FormControl(eventoRawValue.eventoTipoNombre),
      eventoTipoDescripcion: new FormControl(eventoRawValue.eventoTipoDescripcion),
      estado: new FormControl(eventoRawValue.estado),
      ultimaActualizacion: new FormControl(eventoRawValue.ultimaActualizacion),
    });
  }

  getEvento(form: EventoFormGroup): IEvento | NewEvento {
    return form.getRawValue() as IEvento | NewEvento;
  }

  resetForm(form: EventoFormGroup, evento: EventoFormGroupInput): void {
    const eventoRawValue = { ...this.getFormDefaults(), ...evento };
    form.reset(
      {
        ...eventoRawValue,
        id: { value: eventoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EventoFormDefaults {
    return {
      id: null,
    };
  }
}
