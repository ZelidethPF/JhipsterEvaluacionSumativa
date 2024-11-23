import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICentrosDeSalud } from '../centros-de-salud.model';
import { CentrosDeSaludService } from '../service/centros-de-salud.service';

const centrosDeSaludResolve = (route: ActivatedRouteSnapshot): Observable<null | ICentrosDeSalud> => {
  const id = route.params.id;
  if (id) {
    return inject(CentrosDeSaludService)
      .find(id)
      .pipe(
        mergeMap((centrosDeSalud: HttpResponse<ICentrosDeSalud>) => {
          if (centrosDeSalud.body) {
            return of(centrosDeSalud.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default centrosDeSaludResolve;
