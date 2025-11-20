import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../asientos.test-samples';

import { AsientosFormService } from './asientos-form.service';

describe('Asientos Form Service', () => {
  let service: AsientosFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AsientosFormService);
  });

  describe('Service methods', () => {
    describe('createAsientosFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAsientosFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fila: expect.any(Object),
            columna: expect.any(Object),
            persona: expect.any(Object),
            estado: expect.any(Object),
            venta: expect.any(Object),
            sesion: expect.any(Object),
          }),
        );
      });

      it('passing IAsientos should create a new form with FormGroup', () => {
        const formGroup = service.createAsientosFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fila: expect.any(Object),
            columna: expect.any(Object),
            persona: expect.any(Object),
            estado: expect.any(Object),
            venta: expect.any(Object),
            sesion: expect.any(Object),
          }),
        );
      });
    });

    describe('getAsientos', () => {
      it('should return NewAsientos for default Asientos initial value', () => {
        const formGroup = service.createAsientosFormGroup(sampleWithNewData);

        const asientos = service.getAsientos(formGroup) as any;

        expect(asientos).toMatchObject(sampleWithNewData);
      });

      it('should return NewAsientos for empty Asientos initial value', () => {
        const formGroup = service.createAsientosFormGroup();

        const asientos = service.getAsientos(formGroup) as any;

        expect(asientos).toMatchObject({});
      });

      it('should return IAsientos', () => {
        const formGroup = service.createAsientosFormGroup(sampleWithRequiredData);

        const asientos = service.getAsientos(formGroup) as any;

        expect(asientos).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAsientos should not enable id FormControl', () => {
        const formGroup = service.createAsientosFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAsientos should disable id FormControl', () => {
        const formGroup = service.createAsientosFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
