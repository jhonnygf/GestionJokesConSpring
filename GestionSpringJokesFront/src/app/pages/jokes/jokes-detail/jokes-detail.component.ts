import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JokeDto } from 'src/app/models/jokeDto.model';
import { JokeService } from 'src/app/services/joke.service';


@Component({
  selector: 'app-joke-detail',
  templateUrl: './jokes-detail.component.html',
  styleUrls: ['./jokes-detail.component.css']
})
export class JokeDetailComponent implements OnInit {
  
  joke: JokeDto | null = null;

  
  constructor(
    private route: ActivatedRoute,
    private jokeService: JokeService
  ) {}

  ngOnInit(): void {
    // Obtenemos el id que viene por la URL: /jokes/consultar/:id
    const idString = this.route.snapshot.paramMap.get('id');
    if (idString) {
      const id = parseInt(idString, 10);
      this.cargarJoke(id);
    }
  }

  cargarJoke(id: number): void {
    this.jokeService.getJokeDto(id).subscribe({
      next: (data) => {
        console.log('Chiste consultado:', data);
        this.joke = data;
      },
      error: (err) => {
        console.error('Error al cargar el chiste', err);
      }
    });
  }
}
