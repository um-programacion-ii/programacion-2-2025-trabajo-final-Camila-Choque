import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEvento } from 'app/entities/evento/evento.model';
import { EventoService } from 'app/entities/evento/service/evento.service';
import { IIntegrantes } from '../integrantes.model';
import { IntegrantesService } from '../service/integrantes.service';
import { IntegrantesFormGroup, IntegrantesFormService } from './integrantes-form.service';

@Component({
  selector: 'jhi-integrantes-update',
  templateUrl: './integrantes-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class IntegrantesUpdateComponent implements OnInit {
  isSaving = false;
  integrantes: IIntegrantes | null = null;

  eventosSharedCollection: IEvento[] = [];

  protected integrantesService = inject(IntegrantesService);
  protected integrantesFormService = inject(IntegrantesFormService);
  protected eventoService = inject(EventoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: IntegrantesFormGroup = this.integrantesFormService.createIntegrantesFormGroup();

  compareEvento = (o1: IEvento | null, o2: IEvento | null): boolean => this.eventoService.compareEvento(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ integrantes }) => {
      this.integrantes = integrantes;
      if (integrantes) {
        this.updateForm(integrantes);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const integrantes = this.integrantesFormService.getIntegrantes(this.editForm);
    if (integrantes.id !== null) {
      this.subscribeToSaveResponse(this.integrantesService.update(integrantes));
    } else {
      this.subscribeToSaveResponse(this.integrantesService.create(integrantes));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIntegrantes>>): void {
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

  protected updateForm(integrantes: IIntegrantes): void {
    this.integrantes = integrantes;
    this.integrantesFormService.resetForm(this.editForm, integrantes);

    this.eventosSharedCollection = this.eventoService.addEventoToCollectionIfMissing<IEvento>(
      this.eventosSharedCollection,
      integrantes.evento,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eventoService
      .query()
      .pipe(map((res: HttpResponse<IEvento[]>) => res.body ?? []))
      .pipe(map((eventos: IEvento[]) => this.eventoService.addEventoToCollectionIfMissing<IEvento>(eventos, this.integrantes?.evento)))
      .subscribe((eventos: IEvento[]) => (this.eventosSharedCollection = eventos));
  }
}
