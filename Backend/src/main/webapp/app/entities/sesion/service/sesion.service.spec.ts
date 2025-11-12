import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ISesion } from '../sesion.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../sesion.test-samples';

import { RestSesion, SesionService } from './sesion.service';

const requireRestSample: RestSesion = {
  ...sampleWithRequiredData,
  ultimaActividad: sampleWithRequiredData.ultimaActividad?.format(DATE_FORMAT),
  expiraEn: sampleWithRequiredData.expiraEn?.format(DATE_FORMAT),
};

describe('Sesion Service', () => {
  let service: SesionService;
  let httpMock: HttpTestingController;
  let expectedResult: ISesion | ISesion[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SesionService);
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

    it('should create a Sesion', () => {
      const sesion = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(sesion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Sesion', () => {
      const sesion = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(sesion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Sesion', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Sesion', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Sesion', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSesionToCollectionIfMissing', () => {
      it('should add a Sesion to an empty array', () => {
        const sesion: ISesion = sampleWithRequiredData;
        expectedResult = service.addSesionToCollectionIfMissing([], sesion);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sesion);
      });

      it('should not add a Sesion to an array that contains it', () => {
        const sesion: ISesion = sampleWithRequiredData;
        const sesionCollection: ISesion[] = [
          {
            ...sesion,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSesionToCollectionIfMissing(sesionCollection, sesion);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Sesion to an array that doesn't contain it", () => {
        const sesion: ISesion = sampleWithRequiredData;
        const sesionCollection: ISesion[] = [sampleWithPartialData];
        expectedResult = service.addSesionToCollectionIfMissing(sesionCollection, sesion);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sesion);
      });

      it('should add only unique Sesion to an array', () => {
        const sesionArray: ISesion[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const sesionCollection: ISesion[] = [sampleWithRequiredData];
        expectedResult = service.addSesionToCollectionIfMissing(sesionCollection, ...sesionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sesion: ISesion = sampleWithRequiredData;
        const sesion2: ISesion = sampleWithPartialData;
        expectedResult = service.addSesionToCollectionIfMissing([], sesion, sesion2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sesion);
        expect(expectedResult).toContain(sesion2);
      });

      it('should accept null and undefined values', () => {
        const sesion: ISesion = sampleWithRequiredData;
        expectedResult = service.addSesionToCollectionIfMissing([], null, sesion, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sesion);
      });

      it('should return initial array if no Sesion is added', () => {
        const sesionCollection: ISesion[] = [sampleWithRequiredData];
        expectedResult = service.addSesionToCollectionIfMissing(sesionCollection, undefined, null);
        expect(expectedResult).toEqual(sesionCollection);
      });
    });

    describe('compareSesion', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSesion(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 6272 };
        const entity2 = null;

        const compareResult1 = service.compareSesion(entity1, entity2);
        const compareResult2 = service.compareSesion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 6272 };
        const entity2 = { id: 14634 };

        const compareResult1 = service.compareSesion(entity1, entity2);
        const compareResult2 = service.compareSesion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 6272 };
        const entity2 = { id: 6272 };

        const compareResult1 = service.compareSesion(entity1, entity2);
        const compareResult2 = service.compareSesion(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
