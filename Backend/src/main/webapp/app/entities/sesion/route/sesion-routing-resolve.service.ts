import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISesion } from '../sesion.model';
import { SesionService } from '../service/sesion.service';

const sesionResolve = (route: ActivatedRouteSnapshot): Observable<null | ISesion> => {
  const id = route.params.id;
  if (id) {
    return inject(SesionService)
      .find(id)
      .pipe(
        mergeMap((sesion: HttpResponse<ISesion>) => {
          if (sesion.body) {
            return of(sesion.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default sesionResolve;
