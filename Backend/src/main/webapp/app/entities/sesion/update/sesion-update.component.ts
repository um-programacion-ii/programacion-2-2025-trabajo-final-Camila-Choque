import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISesion } from '../sesion.model';
import { SesionService } from '../service/sesion.service';
import { SesionFormGroup, SesionFormService } from './sesion-form.service';

@Component({
  selector: 'jhi-sesion-update',
  templateUrl: './sesion-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SesionUpdateComponent implements OnInit {
  isSaving = false;
  sesion: ISesion | null = null;

  protected sesionService = inject(SesionService);
  protected sesionFormService = inject(SesionFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SesionFormGroup = this.sesionFormService.createSesionFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sesion }) => {
      this.sesion = sesion;
      if (sesion) {
        this.updateForm(sesion);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sesion = this.sesionFormService.getSesion(this.editForm);
    if (sesion.id !== null) {
      this.subscribeToSaveResponse(this.sesionService.update(sesion));
    } else {
      this.subscribeToSaveResponse(this.sesionService.create(sesion));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISesion>>): void {
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

  protected updateForm(sesion: ISesion): void {
    this.sesion = sesion;
    this.sesionFormService.resetForm(this.editForm, sesion);
  }
}
