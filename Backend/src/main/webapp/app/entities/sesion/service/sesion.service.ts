import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISesion, NewSesion } from '../sesion.model';

export type PartialUpdateSesion = Partial<ISesion> & Pick<ISesion, 'id'>;

type RestOf<T extends ISesion | NewSesion> = Omit<T, 'ultimaActividad' | 'expiraEn'> & {
  ultimaActividad?: string | null;
  expiraEn?: string | null;
};

export type RestSesion = RestOf<ISesion>;

export type NewRestSesion = RestOf<NewSesion>;

export type PartialUpdateRestSesion = RestOf<PartialUpdateSesion>;

export type EntityResponseType = HttpResponse<ISesion>;
export type EntityArrayResponseType = HttpResponse<ISesion[]>;

@Injectable({ providedIn: 'root' })
export class SesionService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sesions');

  create(sesion: NewSesion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sesion);
    return this.http
      .post<RestSesion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(sesion: ISesion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sesion);
    return this.http
      .put<RestSesion>(`${this.resourceUrl}/${this.getSesionIdentifier(sesion)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(sesion: PartialUpdateSesion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sesion);
    return this.http
      .patch<RestSesion>(`${this.resourceUrl}/${this.getSesionIdentifier(sesion)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSesion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSesion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSesionIdentifier(sesion: Pick<ISesion, 'id'>): number {
    return sesion.id;
  }

  compareSesion(o1: Pick<ISesion, 'id'> | null, o2: Pick<ISesion, 'id'> | null): boolean {
    return o1 && o2 ? this.getSesionIdentifier(o1) === this.getSesionIdentifier(o2) : o1 === o2;
  }

  addSesionToCollectionIfMissing<Type extends Pick<ISesion, 'id'>>(
    sesionCollection: Type[],
    ...sesionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sesions: Type[] = sesionsToCheck.filter(isPresent);
    if (sesions.length > 0) {
      const sesionCollectionIdentifiers = sesionCollection.map(sesionItem => this.getSesionIdentifier(sesionItem));
      const sesionsToAdd = sesions.filter(sesionItem => {
        const sesionIdentifier = this.getSesionIdentifier(sesionItem);
        if (sesionCollectionIdentifiers.includes(sesionIdentifier)) {
          return false;
        }
        sesionCollectionIdentifiers.push(sesionIdentifier);
        return true;
      });
      return [...sesionsToAdd, ...sesionCollection];
    }
    return sesionCollection;
  }

  protected convertDateFromClient<T extends ISesion | NewSesion | PartialUpdateSesion>(sesion: T): RestOf<T> {
    return {
      ...sesion,
      ultimaActividad: sesion.ultimaActividad?.format(DATE_FORMAT) ?? null,
      expiraEn: sesion.expiraEn?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restSesion: RestSesion): ISesion {
    return {
      ...restSesion,
      ultimaActividad: restSesion.ultimaActividad ? dayjs(restSesion.ultimaActividad) : undefined,
      expiraEn: restSesion.expiraEn ? dayjs(restSesion.expiraEn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSesion>): HttpResponse<ISesion> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSesion[]>): HttpResponse<ISesion[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
