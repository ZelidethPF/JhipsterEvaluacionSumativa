import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICentrosDeSalud, NewCentrosDeSalud } from '../centros-de-salud.model';

export type PartialUpdateCentrosDeSalud = Partial<ICentrosDeSalud> & Pick<ICentrosDeSalud, 'id'>;

export type EntityResponseType = HttpResponse<ICentrosDeSalud>;
export type EntityArrayResponseType = HttpResponse<ICentrosDeSalud[]>;

@Injectable({ providedIn: 'root' })
export class CentrosDeSaludService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/centros-de-saluds');

  create(centrosDeSalud: NewCentrosDeSalud): Observable<EntityResponseType> {
    return this.http.post<ICentrosDeSalud>(this.resourceUrl, centrosDeSalud, { observe: 'response' });
  }

  update(centrosDeSalud: ICentrosDeSalud): Observable<EntityResponseType> {
    return this.http.put<ICentrosDeSalud>(`${this.resourceUrl}/${this.getCentrosDeSaludIdentifier(centrosDeSalud)}`, centrosDeSalud, {
      observe: 'response',
    });
  }

  partialUpdate(centrosDeSalud: PartialUpdateCentrosDeSalud): Observable<EntityResponseType> {
    return this.http.patch<ICentrosDeSalud>(`${this.resourceUrl}/${this.getCentrosDeSaludIdentifier(centrosDeSalud)}`, centrosDeSalud, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICentrosDeSalud>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICentrosDeSalud[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCentrosDeSaludIdentifier(centrosDeSalud: Pick<ICentrosDeSalud, 'id'>): number {
    return centrosDeSalud.id;
  }

  compareCentrosDeSalud(o1: Pick<ICentrosDeSalud, 'id'> | null, o2: Pick<ICentrosDeSalud, 'id'> | null): boolean {
    return o1 && o2 ? this.getCentrosDeSaludIdentifier(o1) === this.getCentrosDeSaludIdentifier(o2) : o1 === o2;
  }

  addCentrosDeSaludToCollectionIfMissing<Type extends Pick<ICentrosDeSalud, 'id'>>(
    centrosDeSaludCollection: Type[],
    ...centrosDeSaludsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const centrosDeSaluds: Type[] = centrosDeSaludsToCheck.filter(isPresent);
    if (centrosDeSaluds.length > 0) {
      const centrosDeSaludCollectionIdentifiers = centrosDeSaludCollection.map(centrosDeSaludItem =>
        this.getCentrosDeSaludIdentifier(centrosDeSaludItem),
      );
      const centrosDeSaludsToAdd = centrosDeSaluds.filter(centrosDeSaludItem => {
        const centrosDeSaludIdentifier = this.getCentrosDeSaludIdentifier(centrosDeSaludItem);
        if (centrosDeSaludCollectionIdentifiers.includes(centrosDeSaludIdentifier)) {
          return false;
        }
        centrosDeSaludCollectionIdentifiers.push(centrosDeSaludIdentifier);
        return true;
      });
      return [...centrosDeSaludsToAdd, ...centrosDeSaludCollection];
    }
    return centrosDeSaludCollection;
  }
}
