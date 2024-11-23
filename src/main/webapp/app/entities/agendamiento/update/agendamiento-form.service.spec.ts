import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../agendamiento.test-samples';

import { AgendamientoFormService } from './agendamiento-form.service';

describe('Agendamiento Form Service', () => {
  let service: AgendamientoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AgendamientoFormService);
  });

  describe('Service methods', () => {
    describe('createAgendamientoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAgendamientoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            paciente: expect.any(Object),
            medico: expect.any(Object),
            centro: expect.any(Object),
            fechahora: expect.any(Object),
            estado: expect.any(Object),
            origen: expect.any(Object),
            motivo: expect.any(Object),
            fechaingreso: expect.any(Object),
            nivelprioridad: expect.any(Object),
          }),
        );
      });

      it('passing IAgendamiento should create a new form with FormGroup', () => {
        const formGroup = service.createAgendamientoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            paciente: expect.any(Object),
            medico: expect.any(Object),
            centro: expect.any(Object),
            fechahora: expect.any(Object),
            estado: expect.any(Object),
            origen: expect.any(Object),
            motivo: expect.any(Object),
            fechaingreso: expect.any(Object),
            nivelprioridad: expect.any(Object),
          }),
        );
      });
    });

    describe('getAgendamiento', () => {
      it('should return NewAgendamiento for default Agendamiento initial value', () => {
        const formGroup = service.createAgendamientoFormGroup(sampleWithNewData);

        const agendamiento = service.getAgendamiento(formGroup) as any;

        expect(agendamiento).toMatchObject(sampleWithNewData);
      });

      it('should return NewAgendamiento for empty Agendamiento initial value', () => {
        const formGroup = service.createAgendamientoFormGroup();

        const agendamiento = service.getAgendamiento(formGroup) as any;

        expect(agendamiento).toMatchObject({});
      });

      it('should return IAgendamiento', () => {
        const formGroup = service.createAgendamientoFormGroup(sampleWithRequiredData);

        const agendamiento = service.getAgendamiento(formGroup) as any;

        expect(agendamiento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAgendamiento should not enable id FormControl', () => {
        const formGroup = service.createAgendamientoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAgendamiento should disable id FormControl', () => {
        const formGroup = service.createAgendamientoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
