import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CentrosDeSaludDetailComponent } from './centros-de-salud-detail.component';

describe('CentrosDeSalud Management Detail Component', () => {
  let comp: CentrosDeSaludDetailComponent;
  let fixture: ComponentFixture<CentrosDeSaludDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CentrosDeSaludDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./centros-de-salud-detail.component').then(m => m.CentrosDeSaludDetailComponent),
              resolve: { centrosDeSalud: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CentrosDeSaludDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CentrosDeSaludDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load centrosDeSalud on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CentrosDeSaludDetailComponent);

      // THEN
      expect(instance.centrosDeSalud()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
