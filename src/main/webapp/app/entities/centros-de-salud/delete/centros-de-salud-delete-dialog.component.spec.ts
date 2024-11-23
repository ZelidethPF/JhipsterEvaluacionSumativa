jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { CentrosDeSaludService } from '../service/centros-de-salud.service';

import { CentrosDeSaludDeleteDialogComponent } from './centros-de-salud-delete-dialog.component';

describe('CentrosDeSalud Management Delete Component', () => {
  let comp: CentrosDeSaludDeleteDialogComponent;
  let fixture: ComponentFixture<CentrosDeSaludDeleteDialogComponent>;
  let service: CentrosDeSaludService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CentrosDeSaludDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(CentrosDeSaludDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CentrosDeSaludDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CentrosDeSaludService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
