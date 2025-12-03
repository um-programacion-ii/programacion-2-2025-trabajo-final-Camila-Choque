import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIntegrantes } from '../integrantes.model';
import { IntegrantesService } from '../service/integrantes.service';

const integrantesResolve = (route: ActivatedRouteSnapshot): Observable<null | IIntegrantes> => {
  const id = route.params.id;
  if (id) {
    return inject(IntegrantesService)
      .find(id)
      .pipe(
        mergeMap((integrantes: HttpResponse<IIntegrantes>) => {
          if (integrantes.body) {
            return of(integrantes.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default integrantesResolve;
