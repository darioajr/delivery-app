import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMotoboy } from 'app/shared/model/motoboy.model';

type EntityResponseType = HttpResponse<IMotoboy>;
type EntityArrayResponseType = HttpResponse<IMotoboy[]>;

@Injectable({ providedIn: 'root' })
export class MotoboyService {
  public resourceUrl = SERVER_API_URL + 'api/motoboys';

  constructor(protected http: HttpClient) {}

  create(motoboy: IMotoboy): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(motoboy);
    return this.http
      .post<IMotoboy>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(motoboy: IMotoboy): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(motoboy);
    return this.http
      .put<IMotoboy>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMotoboy>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMotoboy[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(motoboy: IMotoboy): IMotoboy {
    const copy: IMotoboy = Object.assign({}, motoboy, {
      data: motoboy.data != null && motoboy.data.isValid() ? motoboy.data.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data != null ? moment(res.body.data) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((motoboy: IMotoboy) => {
        motoboy.data = motoboy.data != null ? moment(motoboy.data) : null;
      });
    }
    return res;
  }
}
