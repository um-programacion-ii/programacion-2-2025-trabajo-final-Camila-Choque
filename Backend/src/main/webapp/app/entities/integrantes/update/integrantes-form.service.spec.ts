import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../integrantes.test-samples';

import { IntegrantesFormService } from './integrantes-form.service';

describe('Integrantes Form Service', () => {
  let service: IntegrantesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IntegrantesFormService);
  });

  describe('Service methods', () => {
    describe('createIntegrantesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIntegrantesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            apellido: expect.any(Object),
            identificacion: expect.any(Object),
          }),
        );
      });

      it('passing IIntegrantes should create a new form with FormGroup', () => {
        const formGroup = service.createIntegrantesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            apellido: expect.any(Object),
            identificacion: expect.any(Object),
          }),
        );
      });
    });

    describe('getIntegrantes', () => {
      it('should return NewIntegrantes for default Integrantes initial value', () => {
        const formGroup = service.createIntegrantesFormGroup(sampleWithNewData);

        const integrantes = service.getIntegrantes(formGroup) as any;

        expect(integrantes).toMatchObject(sampleWithNewData);
      });

      it('should return NewIntegrantes for empty Integrantes initial value', () => {
        const formGroup = service.createIntegrantesFormGroup();

        const integrantes = service.getIntegrantes(formGroup) as any;

        expect(integrantes).toMatchObject({});
      });

      it('should return IIntegrantes', () => {
        const formGroup = service.createIntegrantesFormGroup(sampleWithRequiredData);

        const integrantes = service.getIntegrantes(formGroup) as any;

        expect(integrantes).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIntegrantes should not enable id FormControl', () => {
        const formGroup = service.createIntegrantesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIntegrantes should disable id FormControl', () => {
        const formGroup = service.createIntegrantesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
