import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Flags } from '../models/flags.model';

@Injectable({
  providedIn: 'root',
})
export class FlagService {
  private baseUrl = 'http://localhost:8080/api/flags'; 

  constructor(private http: HttpClient) {}
  getFlagsWithJokes(): Observable<Flags[]> {
    return this.http.get<Flags[]>(this.baseUrl);
  }
  // Obtener todos los chistes
  getFlags(): Observable<Flags[]> {
    return this.http.get<Flags[]>(`${this.baseUrl}`);
  }

  // Obtener un chiste por su ID
  getFlag(id: number): Observable<Flags> {
    return this.http.get<Flags>(`${this.baseUrl}/${id}`);
  }

  // Crear un nuevo chiste
  createFlag(flag : Flags): Observable<Flags> {
    return this.http.post<Flags>("http://localhost:8080/api/flags/nuevo", flag);
  }

  // Actualizar un chiste existente
  updateFlag(id: number, flag : Flags): Observable<Flags> {
    return this.http.put<Flags>(`http://localhost:8080/api/flags/update/${id}`, flag);
  }
  

  // Eliminar un chiste
  deleteFlag(id: number): Observable<void> {
    return this.http.delete<void>(`http://localhost:8080/api/flags/borrar/${id}`);
  }

  getUsedIds(): Observable<number[]> {
    return this.http.get<number[]>(`${this.baseUrl}/used-ids`);
  }

  /**
   * 💡 Nueva función para **desvincular una flag de todos los chistes**
   */
  desvincularJokes(flagId: number): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/desvincular/${flagId}`, {});
  }

  deleteFlagAndJokes(flagId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/borrarConJokes/${flagId}`);
  }
  
}