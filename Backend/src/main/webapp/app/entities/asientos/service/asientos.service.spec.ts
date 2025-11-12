import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAsientos } from '../asientos.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../asientos.test-samples';

import { AsientosService } from './asientos.service';

const requireRestSample: IAsientos = {
  ...sampleWithRequiredData,
};

describe('Asientos Service', () => {
  let service: AsientosService;
  let httpMock: HttpTestingController;
  let expectedResult: IAsientos | IAsientos[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AsientosService);
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

    it('should create a Asientos', () => {
      const asientos = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(asientos).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Asientos', () => {
      const asientos = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(asientos).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Asientos', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Asientos', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Asientos', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAsientosToCollectionIfMissing', () => {
      it('should add a Asientos to an empty array', () => {
        const asientos: IAsientos = sampleWithRequiredData;
        expectedResult = service.addAsientosToCollectionIfMissing([], asientos);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(asientos);
      });

      it('should not add a Asientos to an array that contains it', () => {
        const asientos: IAsientos = sampleWithRequiredData;
        const asientosCollection: IAsientos[] = [
          {
            ...asientos,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAsientosToCollectionIfMissing(asientosCollection, asientos);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Asientos to an array that doesn't contain it", () => {
        const asientos: IAsientos = sampleWithRequiredData;
        const asientosCollection: IAsientos[] = [sampleWithPartialData];
        expectedResult = service.addAsientosToCollectionIfMissing(asientosCollection, asientos);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(asientos);
      });

      it('should add only unique Asientos to an array', () => {
        const asientosArray: IAsientos[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const asientosCollection: IAsientos[] = [sampleWithRequiredData];
        expectedResult = service.addAsientosToCollectionIfMissing(asientosCollection, ...asientosArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const asientos: IAsientos = sampleWithRequiredData;
        const asientos2: IAsientos = sampleWithPartialData;
        expectedResult = service.addAsientosToCollectionIfMissing([], asientos, asientos2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(asientos);
        expect(expectedResult).toContain(asientos2);
      });

      it('should accept null and undefined values', () => {
        const asientos: IAsientos = sampleWithRequiredData;
        expectedResult = service.addAsientosToCollectionIfMissing([], null, asientos, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(asientos);
      });

      it('should return initial array if no Asientos is added', () => {
        const asientosCollection: IAsientos[] = [sampleWithRequiredData];
        expectedResult = service.addAsientosToCollectionIfMissing(asientosCollection, undefined, null);
        expect(expectedResult).toEqual(asientosCollection);
      });
    });

    describe('compareAsientos', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAsientos(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 30058 };
        const entity2 = null;

        const compareResult1 = service.compareAsientos(entity1, entity2);
        const compareResult2 = service.compareAsientos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 30058 };
        const entity2 = { id: 12575 };

        const compareResult1 = service.compareAsientos(entity1, entity2);
        const compareResult2 = service.compareAsientos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 30058 };
        const entity2 = { id: 30058 };

        const compareResult1 = service.compareAsientos(entity1, entity2);
        const compareResult2 = service.compareAsientos(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
