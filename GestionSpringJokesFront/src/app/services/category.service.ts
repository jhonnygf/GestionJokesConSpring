import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Categories } from '../models/categories.model';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private baseUrl = 'http://localhost:8080/api/categories'; // Cambia según la configuración de tu backend

  constructor(private http: HttpClient) {}

  // Obtener todas las categorías
  getCategories(): Observable<Categories[]> {
    return this.http.get<Categories[]>(`${this.baseUrl}`);
  }

  // Obtener una categoría por su ID
  getCategory(id: number): Observable<Categories> {
    return this.http.get<Categories>(`${this.baseUrl}/${id}`);
  }

  // Crear una nueva categoría
  createCategory(category: Categories): Observable<Categories> {
    return this.http.post<Categories>(`${this.baseUrl}/nuevo`, category);
  }

  // Actualizar una categoría existente
  updateCategory(id: number, category: Categories): Observable<Categories> {
    return this.http.put<Categories>(`${this.baseUrl}/update/${id}`, category);
  }

  // Eliminar una categoría
  deleteCategory(id: number, borrarJokes: boolean): Observable<void> {
    return this.http.delete<void>(
      `http://localhost:8080/api/categories/borrar/${id}?borrarJokes=${borrarJokes}`
    );
  }
  

  getUsedIds(): Observable<number[]> {
    return this.http.get<number[]>(`${this.baseUrl}/used-ids`);
  }

}
