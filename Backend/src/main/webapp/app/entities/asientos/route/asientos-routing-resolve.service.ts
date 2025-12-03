import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAsientos } from '../asientos.model';
import { AsientosService } from '../service/asientos.service';

const asientosResolve = (route: ActivatedRouteSnapshot): Observable<null | IAsientos> => {
  const id = route.params.id;
  if (id) {
    return inject(AsientosService)
      .find(id)
      .pipe(
        mergeMap((asientos: HttpResponse<IAsientos>) => {
          if (asientos.body) {
            return of(asientos.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default asientosResolve;
