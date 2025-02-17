import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';

export interface Telefono {
  numero: string;
}

export interface PrimeraVez {
  id: number;
  programa: string;
  fechaEmision: string;
  jokeId: number;
  telefonos: Telefono[]; 
}

@Injectable({
  providedIn: 'root'
})
export class PrimeraVezService {
  private baseUrl = 'http://localhost:8080/api/primera-vez';

  constructor(private http: HttpClient) { }

  getAll(): Observable<PrimeraVez[]> {
    return this.http.get<PrimeraVez[]>(`${this.baseUrl}/`).pipe(
      map((data: any[]) => data.map(item => {
        // Si item.telefonos es un array de strings, lo convertimos en array de objetos
        if (item.telefonos && item.telefonos.length > 0 && typeof item.telefonos[0] === 'string') {
          item.telefonos = item.telefonos.map((tel: string) => ({ numero: tel }));
        }
        return item;
      }))
    );
  }

  create(pv: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/nuevo`, pv);
  }

  update(id: number, pv: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/update/${id}`, pv);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/borrar/${id}`);
  }
}
