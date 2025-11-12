import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../sesion.test-samples';

import { SesionFormService } from './sesion-form.service';

describe('Sesion Form Service', () => {
  let service: SesionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SesionFormService);
  });

  describe('Service methods', () => {
    describe('createSesionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSesionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            token: expect.any(Object),
            estadoFlujo: expect.any(Object),
            eventoSeleccionado: expect.any(Object),
            ultimaActividad: expect.any(Object),
            expiraEn: expect.any(Object),
          }),
        );
      });

      it('passing ISesion should create a new form with FormGroup', () => {
        const formGroup = service.createSesionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            token: expect.any(Object),
            estadoFlujo: expect.any(Object),
            eventoSeleccionado: expect.any(Object),
            ultimaActividad: expect.any(Object),
            expiraEn: expect.any(Object),
          }),
        );
      });
    });

    describe('getSesion', () => {
      it('should return NewSesion for default Sesion initial value', () => {
        const formGroup = service.createSesionFormGroup(sampleWithNewData);

        const sesion = service.getSesion(formGroup) as any;

        expect(sesion).toMatchObject(sampleWithNewData);
      });

      it('should return NewSesion for empty Sesion initial value', () => {
        const formGroup = service.createSesionFormGroup();

        const sesion = service.getSesion(formGroup) as any;

        expect(sesion).toMatchObject({});
      });

      it('should return ISesion', () => {
        const formGroup = service.createSesionFormGroup(sampleWithRequiredData);

        const sesion = service.getSesion(formGroup) as any;

        expect(sesion).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISesion should not enable id FormControl', () => {
        const formGroup = service.createSesionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSesion should disable id FormControl', () => {
        const formGroup = service.createSesionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
