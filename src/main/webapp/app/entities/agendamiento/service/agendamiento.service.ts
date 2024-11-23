import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAgendamiento, NewAgendamiento } from '../agendamiento.model';

export type PartialUpdateAgendamiento = Partial<IAgendamiento> & Pick<IAgendamiento, 'id'>;

type RestOf<T extends IAgendamiento | NewAgendamiento> = Omit<T, 'fechahora' | 'fechaingreso'> & {
  fechahora?: string | null;
  fechaingreso?: string | null;
};

export type RestAgendamiento = RestOf<IAgendamiento>;

export type NewRestAgendamiento = RestOf<NewAgendamiento>;

export type PartialUpdateRestAgendamiento = RestOf<PartialUpdateAgendamiento>;

export type EntityResponseType = HttpResponse<IAgendamiento>;
export type EntityArrayResponseType = HttpResponse<IAgendamiento[]>;

@Injectable({ providedIn: 'root' })
export class AgendamientoService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/agendamientos');

  create(agendamiento: NewAgendamiento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agendamiento);
    return this.http
      .post<RestAgendamiento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(agendamiento: IAgendamiento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agendamiento);
    return this.http
      .put<RestAgendamiento>(`${this.resourceUrl}/${this.getAgendamientoIdentifier(agendamiento)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(agendamiento: PartialUpdateAgendamiento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agendamiento);
    return this.http
      .patch<RestAgendamiento>(`${this.resourceUrl}/${this.getAgendamientoIdentifier(agendamiento)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAgendamiento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAgendamiento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAgendamientoIdentifier(agendamiento: Pick<IAgendamiento, 'id'>): number {
    return agendamiento.id;
  }

  compareAgendamiento(o1: Pick<IAgendamiento, 'id'> | null, o2: Pick<IAgendamiento, 'id'> | null): boolean {
    return o1 && o2 ? this.getAgendamientoIdentifier(o1) === this.getAgendamientoIdentifier(o2) : o1 === o2;
  }

  addAgendamientoToCollectionIfMissing<Type extends Pick<IAgendamiento, 'id'>>(
    agendamientoCollection: Type[],
    ...agendamientosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const agendamientos: Type[] = agendamientosToCheck.filter(isPresent);
    if (agendamientos.length > 0) {
      const agendamientoCollectionIdentifiers = agendamientoCollection.map(agendamientoItem =>
        this.getAgendamientoIdentifier(agendamientoItem),
      );
      const agendamientosToAdd = agendamientos.filter(agendamientoItem => {
        const agendamientoIdentifier = this.getAgendamientoIdentifier(agendamientoItem);
        if (agendamientoCollectionIdentifiers.includes(agendamientoIdentifier)) {
          return false;
        }
        agendamientoCollectionIdentifiers.push(agendamientoIdentifier);
        return true;
      });
      return [...agendamientosToAdd, ...agendamientoCollection];
    }
    return agendamientoCollection;
  }

  protected convertDateFromClient<T extends IAgendamiento | NewAgendamiento | PartialUpdateAgendamiento>(agendamiento: T): RestOf<T> {
    return {
      ...agendamiento,
      fechahora: agendamiento.fechahora?.toJSON() ?? null,
      fechaingreso: agendamiento.fechaingreso?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restAgendamiento: RestAgendamiento): IAgendamiento {
    return {
      ...restAgendamiento,
      fechahora: restAgendamiento.fechahora ? dayjs(restAgendamiento.fechahora) : undefined,
      fechaingreso: restAgendamiento.fechaingreso ? dayjs(restAgendamiento.fechaingreso) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAgendamiento>): HttpResponse<IAgendamiento> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAgendamiento[]>): HttpResponse<IAgendamiento[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
