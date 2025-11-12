import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAsientos } from '../asientos.model';
import { AsientosService } from '../service/asientos.service';
import { AsientosFormGroup, AsientosFormService } from './asientos-form.service';

@Component({
  selector: 'jhi-asientos-update',
  templateUrl: './asientos-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AsientosUpdateComponent implements OnInit {
  isSaving = false;
  asientos: IAsientos | null = null;

  protected asientosService = inject(AsientosService);
  protected asientosFormService = inject(AsientosFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AsientosFormGroup = this.asientosFormService.createAsientosFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ asientos }) => {
      this.asientos = asientos;
      if (asientos) {
        this.updateForm(asientos);
      }
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
  }
}
