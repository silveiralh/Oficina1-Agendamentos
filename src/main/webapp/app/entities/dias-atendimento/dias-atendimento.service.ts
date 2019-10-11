import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDiasAtendimento } from 'app/shared/model/dias-atendimento.model';

type EntityResponseType = HttpResponse<IDiasAtendimento>;
type EntityArrayResponseType = HttpResponse<IDiasAtendimento[]>;

@Injectable({ providedIn: 'root' })
export class DiasAtendimentoService {
  public resourceUrl = SERVER_API_URL + 'api/dias-atendimentos';

  constructor(protected http: HttpClient) {}

  create(diasAtendimento: IDiasAtendimento): Observable<EntityResponseType> {
    return this.http.post<IDiasAtendimento>(this.resourceUrl, diasAtendimento, { observe: 'response' });
  }

  update(diasAtendimento: IDiasAtendimento): Observable<EntityResponseType> {
    return this.http.put<IDiasAtendimento>(this.resourceUrl, diasAtendimento, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDiasAtendimento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDiasAtendimento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
