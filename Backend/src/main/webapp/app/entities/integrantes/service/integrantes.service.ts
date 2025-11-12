import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIntegrantes, NewIntegrantes } from '../integrantes.model';

export type PartialUpdateIntegrantes = Partial<IIntegrantes> & Pick<IIntegrantes, 'id'>;

export type EntityResponseType = HttpResponse<IIntegrantes>;
export type EntityArrayResponseType = HttpResponse<IIntegrantes[]>;

@Injectable({ providedIn: 'root' })
export class IntegrantesService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/integrantes');

  create(integrantes: NewIntegrantes): Observable<EntityResponseType> {
    return this.http.post<IIntegrantes>(this.resourceUrl, integrantes, { observe: 'response' });
  }

  update(integrantes: IIntegrantes): Observable<EntityResponseType> {
    return this.http.put<IIntegrantes>(`${this.resourceUrl}/${this.getIntegrantesIdentifier(integrantes)}`, integrantes, {
      observe: 'response',
    });
  }

  partialUpdate(integrantes: PartialUpdateIntegrantes): Observable<EntityResponseType> {
    return this.http.patch<IIntegrantes>(`${this.resourceUrl}/${this.getIntegrantesIdentifier(integrantes)}`, integrantes, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIntegrantes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIntegrantes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIntegrantesIdentifier(integrantes: Pick<IIntegrantes, 'id'>): number {
    return integrantes.id;
  }

  compareIntegrantes(o1: Pick<IIntegrantes, 'id'> | null, o2: Pick<IIntegrantes, 'id'> | null): boolean {
    return o1 && o2 ? this.getIntegrantesIdentifier(o1) === this.getIntegrantesIdentifier(o2) : o1 === o2;
  }

  addIntegrantesToCollectionIfMissing<Type extends Pick<IIntegrantes, 'id'>>(
    integrantesCollection: Type[],
    ...integrantesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const integrantes: Type[] = integrantesToCheck.filter(isPresent);
    if (integrantes.length > 0) {
      const integrantesCollectionIdentifiers = integrantesCollection.map(integrantesItem => this.getIntegrantesIdentifier(integrantesItem));
      const integrantesToAdd = integrantes.filter(integrantesItem => {
        const integrantesIdentifier = this.getIntegrantesIdentifier(integrantesItem);
        if (integrantesCollectionIdentifiers.includes(integrantesIdentifier)) {
          return false;
        }
        integrantesCollectionIdentifiers.push(integrantesIdentifier);
        return true;
      });
      return [...integrantesToAdd, ...integrantesCollection];
    }
    return integrantesCollection;
  }
}
