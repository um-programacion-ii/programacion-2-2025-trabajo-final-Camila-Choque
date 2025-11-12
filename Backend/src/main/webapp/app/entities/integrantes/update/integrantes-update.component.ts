import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

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

  protected integrantesService = inject(IntegrantesService);
  protected integrantesFormService = inject(IntegrantesFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: IntegrantesFormGroup = this.integrantesFormService.createIntegrantesFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ integrantes }) => {
      this.integrantes = integrantes;
      if (integrantes) {
        this.updateForm(integrantes);
      }
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
  }
}
