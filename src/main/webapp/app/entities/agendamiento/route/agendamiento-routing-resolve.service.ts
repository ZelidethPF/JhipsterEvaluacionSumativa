import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAgendamiento } from '../agendamiento.model';
import { AgendamientoService } from '../service/agendamiento.service';

const agendamientoResolve = (route: ActivatedRouteSnapshot): Observable<null | IAgendamiento> => {
  const id = route.params.id;
  if (id) {
    return inject(AgendamientoService)
      .find(id)
      .pipe(
        mergeMap((agendamiento: HttpResponse<IAgendamiento>) => {
          if (agendamiento.body) {
            return of(agendamiento.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default agendamientoResolve;
