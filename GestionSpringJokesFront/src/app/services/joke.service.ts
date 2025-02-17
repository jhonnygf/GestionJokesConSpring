import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Joke } from '../models/jokes.model';
import { JokeDto } from '../models/jokeDto.model'
import { JokePrimeraVez } from '../models/primera-vez.model';


@Injectable({
  providedIn: 'root',
})
export class JokeService {
  private baseUrl = 'http://localhost:8080/api/jokes'; // Cambia la URL según tu API

  constructor(private http: HttpClient) {}

  // Obtener todos los chistes
  getJokes(): Observable<Joke[]> {
    return this.http.get<Joke[]>(`${this.baseUrl}`);
  }

  // Obtener un chiste por su ID
  getJoke(id: number): Observable<Joke> {
    return this.http.get<Joke>(`${this.baseUrl}/${id}`);
  }

  // Crear un nuevo chiste
  createJoke(joke: Joke): Observable<Joke> {
    return this.http.post<Joke>("http://localhost:8080/api/jokes/nuevo", joke);
  }

  // Actualizar un chiste existente
  updateJoke(id: number, joke: Joke): Observable<Joke> {
    return this.http.put<Joke>(`http://localhost:8080/api/jokes/update/${id}`, joke);
  }
  

  // Eliminar un chiste
  deleteJoke(id: number): Observable<void> {
    return this.http.delete<void>(`http://localhost:8080/api/jokes/borrar/${id}`);
  }

  desvincularJokes(flagId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/desvincular/${flagId}`);
  }
  
  getJokesByIds(ids: string[]): Observable<Joke[]> {
    return this.http.post<Joke[]>(`${this.baseUrl}/getByIds`, { ids });
  }

  getJokeDto(id: number): Observable<JokeDto> {
    return this.http.get<JokeDto>(`${this.baseUrl}/${id}`);
  }

   // 🔹 Obtener Categorías desde el backend
   getCategories(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:8080/api/categories');
  }

  // 🔹 Obtener Idiomas desde el backend
  getLanguages(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:8080/api/languages');
  }

  // 🔹 Obtener Flags desde el backend
  getFlags(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:8080/api/flags');
  }

  getJokesWithPrimeraVez(): Observable<JokePrimeraVez[]> {
    return this.http.get<JokePrimeraVez[]>(`${this.baseUrl}/jokes-con-primera-vez`);
  }
  getJokesConPrimeraVez(): Observable<Joke[]> {
    return this.http.get<Joke[]>(`${this.baseUrl}/primera-vez/list`);
  }


}
