import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Joke } from 'src/app/models/jokes.model';
import { Types } from 'src/app/models/types.model';
import { JokeService } from 'src/app/services/joke.service';
import { TypeService } from 'src/app/services/type.service';

@Component({
  selector: 'app-types-detail',
  templateUrl: './types-detail.component.html',
  styleUrls: ['./types-detail.component.css']
})
export class TypesDetailComponent implements OnInit {
  type: Types | null = null;
  jokes: Joke[] = []; // ✅ Guardamos los chistes completos

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private typeService: TypeService,
    private jokeService: JokeService,  // ✅ Servicio de chistes
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    const typeId = Number(this.route.snapshot.paramMap.get('id'));
    if (typeId) {
      this.loadTypeDetail(typeId);
    }
  }

  loadTypeDetail(id: number): void {
    this.typeService.getType(id).subscribe({
      next: (type) => {
        this.type = type;
        if (type.jokes && type.jokes.length > 0) {
          this.loadJokes(type.jokes); // ✅ Cargar detalles de los chistes
        }
      },
      error: (err) => {
        console.error('Error al cargar el tipo:', err);
        this.snackBar.open('Error al cargar el tipo.', 'Cerrar', { duration: 3000 });
      }
    });
  }

  loadJokes(jokeIds: string[]): void {
    this.jokeService.getJokesByIds(jokeIds).subscribe({
      next: (jokes) => {
        this.jokes = jokes; // ✅ Guardamos los chistes completos
      },
      error: (err) => {
        console.error('Error al cargar chistes:', err);
        this.snackBar.open('Error al cargar chistes.', 'Cerrar', { duration: 3000 });
      }
    });
  }

  volverAtras(): void {
    this.router.navigate(['/types']);
  }
}
