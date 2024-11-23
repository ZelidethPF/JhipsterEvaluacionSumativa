import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IAgendamiento } from '../agendamiento.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../agendamiento.test-samples';

import { AgendamientoService, RestAgendamiento } from './agendamiento.service';

const requireRestSample: RestAgendamiento = {
  ...sampleWithRequiredData,
  fechahora: sampleWithRequiredData.fechahora?.toJSON(),
  fechaingreso: sampleWithRequiredData.fechaingreso?.format(DATE_FORMAT),
};

describe('Agendamiento Service', () => {
  let service: AgendamientoService;
  let httpMock: HttpTestingController;
  let expectedResult: IAgendamiento | IAgendamiento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AgendamientoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Agendamiento', () => {
      const agendamiento = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(agendamiento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Agendamiento', () => {
      const agendamiento = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(agendamiento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Agendamiento', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Agendamiento', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Agendamiento', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAgendamientoToCollectionIfMissing', () => {
      it('should add a Agendamiento to an empty array', () => {
        const agendamiento: IAgendamiento = sampleWithRequiredData;
        expectedResult = service.addAgendamientoToCollectionIfMissing([], agendamiento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agendamiento);
      });

      it('should not add a Agendamiento to an array that contains it', () => {
        const agendamiento: IAgendamiento = sampleWithRequiredData;
        const agendamientoCollection: IAgendamiento[] = [
          {
            ...agendamiento,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAgendamientoToCollectionIfMissing(agendamientoCollection, agendamiento);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Agendamiento to an array that doesn't contain it", () => {
        const agendamiento: IAgendamiento = sampleWithRequiredData;
        const agendamientoCollection: IAgendamiento[] = [sampleWithPartialData];
        expectedResult = service.addAgendamientoToCollectionIfMissing(agendamientoCollection, agendamiento);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agendamiento);
      });

      it('should add only unique Agendamiento to an array', () => {
        const agendamientoArray: IAgendamiento[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const agendamientoCollection: IAgendamiento[] = [sampleWithRequiredData];
        expectedResult = service.addAgendamientoToCollectionIfMissing(agendamientoCollection, ...agendamientoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const agendamiento: IAgendamiento = sampleWithRequiredData;
        const agendamiento2: IAgendamiento = sampleWithPartialData;
        expectedResult = service.addAgendamientoToCollectionIfMissing([], agendamiento, agendamiento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agendamiento);
        expect(expectedResult).toContain(agendamiento2);
      });

      it('should accept null and undefined values', () => {
        const agendamiento: IAgendamiento = sampleWithRequiredData;
        expectedResult = service.addAgendamientoToCollectionIfMissing([], null, agendamiento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agendamiento);
      });

      it('should return initial array if no Agendamiento is added', () => {
        const agendamientoCollection: IAgendamiento[] = [sampleWithRequiredData];
        expectedResult = service.addAgendamientoToCollectionIfMissing(agendamientoCollection, undefined, null);
        expect(expectedResult).toEqual(agendamientoCollection);
      });
    });

    describe('compareAgendamiento', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAgendamiento(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAgendamiento(entity1, entity2);
        const compareResult2 = service.compareAgendamiento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAgendamiento(entity1, entity2);
        const compareResult2 = service.compareAgendamiento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAgendamiento(entity1, entity2);
        const compareResult2 = service.compareAgendamiento(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
