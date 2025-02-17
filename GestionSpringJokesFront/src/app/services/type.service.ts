import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Types } from '../models/types.model';

@Injectable({
  providedIn: 'root'
})
export class TypeService {
  private baseUrl = 'http://localhost:8080/api/types';

  constructor(private http: HttpClient) {}

  getTypes(): Observable<Types[]> {
    return this.http.get<Types[]>(`${this.baseUrl}/`);
  }

  getType(id: number): Observable<Types> {
    return this.http.get<Types>(`${this.baseUrl}/${id}`);
  }

  createType(type: Types): Observable<Types> {
    return this.http.post<Types>(`${this.baseUrl}/nuevo`, type);
  }

  updateType(id: number, type: Types): Observable<Types> {
    return this.http.put<Types>(`${this.baseUrl}/update/${id}`, type);
  }

  deleteType(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/borrar/${id}`);
  }

  desvincularJokes(typeId: number): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/desvincular/${typeId}`, {});
  }
}
