import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../centros-de-salud.test-samples';

import { CentrosDeSaludFormService } from './centros-de-salud-form.service';

describe('CentrosDeSalud Form Service', () => {
  let service: CentrosDeSaludFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CentrosDeSaludFormService);
  });

  describe('Service methods', () => {
    describe('createCentrosDeSaludFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCentrosDeSaludFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            tipo: expect.any(Object),
            ciudad: expect.any(Object),
            direccion: expect.any(Object),
            telefono: expect.any(Object),
            email: expect.any(Object),
          }),
        );
      });

      it('passing ICentrosDeSalud should create a new form with FormGroup', () => {
        const formGroup = service.createCentrosDeSaludFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            tipo: expect.any(Object),
            ciudad: expect.any(Object),
            direccion: expect.any(Object),
            telefono: expect.any(Object),
            email: expect.any(Object),
          }),
        );
      });
    });

    describe('getCentrosDeSalud', () => {
      it('should return NewCentrosDeSalud for default CentrosDeSalud initial value', () => {
        const formGroup = service.createCentrosDeSaludFormGroup(sampleWithNewData);

        const centrosDeSalud = service.getCentrosDeSalud(formGroup) as any;

        expect(centrosDeSalud).toMatchObject(sampleWithNewData);
      });

      it('should return NewCentrosDeSalud for empty CentrosDeSalud initial value', () => {
        const formGroup = service.createCentrosDeSaludFormGroup();

        const centrosDeSalud = service.getCentrosDeSalud(formGroup) as any;

        expect(centrosDeSalud).toMatchObject({});
      });

      it('should return ICentrosDeSalud', () => {
        const formGroup = service.createCentrosDeSaludFormGroup(sampleWithRequiredData);

        const centrosDeSalud = service.getCentrosDeSalud(formGroup) as any;

        expect(centrosDeSalud).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICentrosDeSalud should not enable id FormControl', () => {
        const formGroup = service.createCentrosDeSaludFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCentrosDeSalud should disable id FormControl', () => {
        const formGroup = service.createCentrosDeSaludFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
