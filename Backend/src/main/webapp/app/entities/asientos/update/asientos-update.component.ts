import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEvento } from 'app/entities/evento/evento.model';
import { EventoService } from 'app/entities/evento/service/evento.service';
import { IVenta } from 'app/entities/venta/venta.model';
import { VentaService } from 'app/entities/venta/service/venta.service';
import { ISesion } from 'app/entities/sesion/sesion.model';
import { SesionService } from 'app/entities/sesion/service/sesion.service';
import { AsientosService } from '../service/asientos.service';
import { IAsientos } from '../asientos.model';
import { AsientosFormGroup, AsientosFormService } from './asientos-form.service';

@Component({
  selector: 'jhi-asientos-update',
  templateUrl: './asientos-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AsientosUpdateComponent implements OnInit {
  isSaving = false;
  asientos: IAsientos | null = null;

  eventosSharedCollection: IEvento[] = [];
  ventasSharedCollection: IVenta[] = [];
  sesionsSharedCollection: ISesion[] = [];

  protected asientosService = inject(AsientosService);
  protected asientosFormService = inject(AsientosFormService);
  protected eventoService = inject(EventoService);
  protected ventaService = inject(VentaService);
  protected sesionService = inject(SesionService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AsientosFormGroup = this.asientosFormService.createAsientosFormGroup();

  compareEvento = (o1: IEvento | null, o2: IEvento | null): boolean => this.eventoService.compareEvento(o1, o2);

  compareVenta = (o1: IVenta | null, o2: IVenta | null): boolean => this.ventaService.compareVenta(o1, o2);

  compareSesion = (o1: ISesion | null, o2: ISesion | null): boolean => this.sesionService.compareSesion(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ asientos }) => {
      this.asientos = asientos;
      if (asientos) {
        this.updateForm(asientos);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const asientos = this.asientosFormService.getAsientos(this.editForm);
    if (asientos.id !== null) {
      this.subscribeToSaveResponse(this.asientosService.update(asientos));
    } else {
      this.subscribeToSaveResponse(this.asientosService.create(asientos));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAsientos>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(asientos: IAsientos): void {
    this.asientos = asientos;
    this.asientosFormService.resetForm(this.editForm, asientos);

    this.eventosSharedCollection = this.eventoService.addEventoToCollectionIfMissing<IEvento>(
      this.eventosSharedCollection,
      asientos.evento,
    );
    this.ventasSharedCollection = this.ventaService.addVentaToCollectionIfMissing<IVenta>(this.ventasSharedCollection, asientos.venta);
    this.sesionsSharedCollection = this.sesionService.addSesionToCollectionIfMissing<ISesion>(
      this.sesionsSharedCollection,
      asientos.sesion,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eventoService
      .query()
      .pipe(map((res: HttpResponse<IEvento[]>) => res.body ?? []))
      .pipe(map((eventos: IEvento[]) => this.eventoService.addEventoToCollectionIfMissing<IEvento>(eventos, this.asientos?.evento)))
      .subscribe((eventos: IEvento[]) => (this.eventosSharedCollection = eventos));

    this.ventaService
      .query()
      .pipe(map((res: HttpResponse<IVenta[]>) => res.body ?? []))
      .pipe(map((ventas: IVenta[]) => this.ventaService.addVentaToCollectionIfMissing<IVenta>(ventas, this.asientos?.venta)))
      .subscribe((ventas: IVenta[]) => (this.ventasSharedCollection = ventas));

    this.sesionService
      .query()
      .pipe(map((res: HttpResponse<ISesion[]>) => res.body ?? []))
      .pipe(map((sesions: ISesion[]) => this.sesionService.addSesionToCollectionIfMissing<ISesion>(sesions, this.asientos?.sesion)))
      .subscribe((sesions: ISesion[]) => (this.sesionsSharedCollection = sesions));
  }
}
