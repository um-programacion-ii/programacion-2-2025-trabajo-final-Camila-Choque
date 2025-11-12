import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAsientos, NewAsientos } from '../asientos.model';

export type PartialUpdateAsientos = Partial<IAsientos> & Pick<IAsientos, 'id'>;

export type EntityResponseType = HttpResponse<IAsientos>;
export type EntityArrayResponseType = HttpResponse<IAsientos[]>;

@Injectable({ providedIn: 'root' })
export class AsientosService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/asientos');

  create(asientos: NewAsientos): Observable<EntityResponseType> {
    return this.http.post<IAsientos>(this.resourceUrl, asientos, { observe: 'response' });
  }

  update(asientos: IAsientos): Observable<EntityResponseType> {
    return this.http.put<IAsientos>(`${this.resourceUrl}/${this.getAsientosIdentifier(asientos)}`, asientos, { observe: 'response' });
  }

  partialUpdate(asientos: PartialUpdateAsientos): Observable<EntityResponseType> {
    return this.http.patch<IAsientos>(`${this.resourceUrl}/${this.getAsientosIdentifier(asientos)}`, asientos, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAsientos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAsientos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAsientosIdentifier(asientos: Pick<IAsientos, 'id'>): number {
    return asientos.id;
  }

  compareAsientos(o1: Pick<IAsientos, 'id'> | null, o2: Pick<IAsientos, 'id'> | null): boolean {
    return o1 && o2 ? this.getAsientosIdentifier(o1) === this.getAsientosIdentifier(o2) : o1 === o2;
  }

  addAsientosToCollectionIfMissing<Type extends Pick<IAsientos, 'id'>>(
    asientosCollection: Type[],
    ...asientosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const asientos: Type[] = asientosToCheck.filter(isPresent);
    if (asientos.length > 0) {
      const asientosCollectionIdentifiers = asientosCollection.map(asientosItem => this.getAsientosIdentifier(asientosItem));
      const asientosToAdd = asientos.filter(asientosItem => {
        const asientosIdentifier = this.getAsientosIdentifier(asientosItem);
        if (asientosCollectionIdentifiers.includes(asientosIdentifier)) {
          return false;
        }
        asientosCollectionIdentifiers.push(asientosIdentifier);
        return true;
      });
      return [...asientosToAdd, ...asientosCollection];
    }
    return asientosCollection;
  }
}
