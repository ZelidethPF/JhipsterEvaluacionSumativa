import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { CentrosDeSaludService } from '../service/centros-de-salud.service';
import { ICentrosDeSalud } from '../centros-de-salud.model';
import { CentrosDeSaludFormService } from './centros-de-salud-form.service';

import { CentrosDeSaludUpdateComponent } from './centros-de-salud-update.component';

describe('CentrosDeSalud Management Update Component', () => {
  let comp: CentrosDeSaludUpdateComponent;
  let fixture: ComponentFixture<CentrosDeSaludUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let centrosDeSaludFormService: CentrosDeSaludFormService;
  let centrosDeSaludService: CentrosDeSaludService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CentrosDeSaludUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CentrosDeSaludUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CentrosDeSaludUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    centrosDeSaludFormService = TestBed.inject(CentrosDeSaludFormService);
    centrosDeSaludService = TestBed.inject(CentrosDeSaludService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const centrosDeSalud: ICentrosDeSalud = { id: 456 };

      activatedRoute.data = of({ centrosDeSalud });
      comp.ngOnInit();

      expect(comp.centrosDeSalud).toEqual(centrosDeSalud);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentrosDeSalud>>();
      const centrosDeSalud = { id: 123 };
      jest.spyOn(centrosDeSaludFormService, 'getCentrosDeSalud').mockReturnValue(centrosDeSalud);
      jest.spyOn(centrosDeSaludService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centrosDeSalud });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: centrosDeSalud }));
      saveSubject.complete();

      // THEN
      expect(centrosDeSaludFormService.getCentrosDeSalud).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(centrosDeSaludService.update).toHaveBeenCalledWith(expect.objectContaining(centrosDeSalud));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentrosDeSalud>>();
      const centrosDeSalud = { id: 123 };
      jest.spyOn(centrosDeSaludFormService, 'getCentrosDeSalud').mockReturnValue({ id: null });
      jest.spyOn(centrosDeSaludService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centrosDeSalud: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: centrosDeSalud }));
      saveSubject.complete();

      // THEN
      expect(centrosDeSaludFormService.getCentrosDeSalud).toHaveBeenCalled();
      expect(centrosDeSaludService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentrosDeSalud>>();
      const centrosDeSalud = { id: 123 };
      jest.spyOn(centrosDeSaludService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centrosDeSalud });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(centrosDeSaludService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
