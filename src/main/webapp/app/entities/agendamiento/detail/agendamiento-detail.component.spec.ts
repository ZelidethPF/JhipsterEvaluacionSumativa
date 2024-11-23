import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AgendamientoDetailComponent } from './agendamiento-detail.component';

describe('Agendamiento Management Detail Component', () => {
  let comp: AgendamientoDetailComponent;
  let fixture: ComponentFixture<AgendamientoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgendamientoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./agendamiento-detail.component').then(m => m.AgendamientoDetailComponent),
              resolve: { agendamiento: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AgendamientoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AgendamientoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load agendamiento on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AgendamientoDetailComponent);

      // THEN
      expect(instance.agendamiento()).toEqual(expect.objectContaining({ id: 123 }));
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
