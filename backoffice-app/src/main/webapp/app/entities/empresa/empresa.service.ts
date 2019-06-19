import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmpresa } from 'app/shared/model/empresa.model';

type EntityResponseType = HttpResponse<IEmpresa>;
type EntityArrayResponseType = HttpResponse<IEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class EmpresaService {
  public resourceUrl = SERVER_API_URL + 'api/empresas';

  constructor(protected http: HttpClient) {}

  create(empresa: IEmpresa): Observable<EntityResponseType> {
    return this.http.post<IEmpresa>(this.resourceUrl, empresa, { observe: 'response' });
  }

  update(empresa: IEmpresa): Observable<EntityResponseType> {
    return this.http.put<IEmpresa>(this.resourceUrl, empresa, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
