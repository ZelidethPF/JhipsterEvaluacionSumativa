import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { AgendamientoService } from '../service/agendamiento.service';
import { IAgendamiento } from '../agendamiento.model';
import { AgendamientoFormService } from './agendamiento-form.service';

import { AgendamientoUpdateComponent } from './agendamiento-update.component';

describe('Agendamiento Management Update Component', () => {
  let comp: AgendamientoUpdateComponent;
  let fixture: ComponentFixture<AgendamientoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let agendamientoFormService: AgendamientoFormService;
  let agendamientoService: AgendamientoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AgendamientoUpdateComponent],
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
      .overrideTemplate(AgendamientoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AgendamientoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    agendamientoFormService = TestBed.inject(AgendamientoFormService);
    agendamientoService = TestBed.inject(AgendamientoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const agendamiento: IAgendamiento = { id: 456 };

      activatedRoute.data = of({ agendamiento });
      comp.ngOnInit();

      expect(comp.agendamiento).toEqual(agendamiento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgendamiento>>();
      const agendamiento = { id: 123 };
      jest.spyOn(agendamientoFormService, 'getAgendamiento').mockReturnValue(agendamiento);
      jest.spyOn(agendamientoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agendamiento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agendamiento }));
      saveSubject.complete();

      // THEN
      expect(agendamientoFormService.getAgendamiento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(agendamientoService.update).toHaveBeenCalledWith(expect.objectContaining(agendamiento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgendamiento>>();
      const agendamiento = { id: 123 };
      jest.spyOn(agendamientoFormService, 'getAgendamiento').mockReturnValue({ id: null });
      jest.spyOn(agendamientoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agendamiento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agendamiento }));
      saveSubject.complete();

      // THEN
      expect(agendamientoFormService.getAgendamiento).toHaveBeenCalled();
      expect(agendamientoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgendamiento>>();
      const agendamiento = { id: 123 };
      jest.spyOn(agendamientoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agendamiento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(agendamientoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
