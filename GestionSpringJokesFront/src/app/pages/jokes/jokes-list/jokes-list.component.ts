import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { JokeService } from '../../../services/joke.service';
import { Joke } from '../../../models/jokes.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { JokesDetailDialogComponent } from 'src/app/jokes-detail-dialog/jokes-detail-dialog.component';
import { JokeDto } from 'src/app/models/jokeDto.model';
import { ConfirmDeleteDialogComponent } from 'src/app/components/confirm-delete-jokes-flags/confirm-delete-dialog.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-jokes-list',
  templateUrl: './jokes-list.component.html',
  styleUrls: ['./jokes-list.component.css'],
})
export class JokesListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'contenido1', 'contenido2', 'categoria', 'idioma', 'flagsCount', 'primeraVez', 'acciones'];  dataSource = new MatTableDataSource<any>();
  filtro: string = '';
  mostrarSinFlags = false;
  allJokes: Joke[] = [];

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private jokeService: JokeService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.cargarJokes();
  }

  cargarJokes(): void {
    this.jokeService.getJokes().subscribe(
      (jokes: Joke[]) => {
        const jokesWithFlags = jokes.map(joke => ({
          ...joke,
          flagsCount: joke.flagses ? joke.flagses.length : 0
        }));
        jokesWithFlags.sort((a, b) => a.id! - b.id!);
        this.allJokes = [...jokesWithFlags];
        this.dataSource = new MatTableDataSource(jokesWithFlags);
        this.dataSource.paginator = this.paginator;
      },
      (error) => {
        this.snackBar.open('Error al cargar los chistes', 'Cerrar', { duration: 3000 });
        console.error('Error al cargar los chistes:', error);
      }
    );
  }

  aplicarFiltro(): void {
    this.dataSource.filter = this.filtro.trim().toLowerCase();
  }

  verDetalle(joke: JokeDto) {
    this.dialog.open(JokesDetailDialogComponent, {
      data: joke
    });
  }

  eliminarJoke(id: number): void {
    const dialogRef = this.dialog.open(ConfirmDeleteDialogComponent, {
      width: '350px',
      data: { message: '¿Estás seguro de que deseas eliminar este chiste?' },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.jokeService.deleteJoke(id).subscribe(
          () => {
            this.dataSource.data = this.dataSource.data.filter(j => j.id !== id);
            this.snackBar.open('¡Chiste eliminado con éxito!', 'Cerrar', { duration: 3000 });
          },
          (error) => {
            console.error('Error al eliminar el chiste:', error);
            this.snackBar.open('Error al eliminar el chiste. Inténtalo de nuevo.', 'Cerrar', { duration: 3000 });
          }
        );
      }
    });
  }

  toggleFilterFlags(): void {
    this.mostrarSinFlags = !this.mostrarSinFlags;

    if (this.mostrarSinFlags) {
      // Filtra los chistes sin flags
      this.dataSource.data = this.allJokes.filter(joke => !joke.flagses || joke.flagses.length === 0);
    } else {
      // Muestra todos los chistes desde la lista completa guardada
      this.dataSource.data = [...this.allJokes];
    }

    // Asegura que la paginación no se pierda al aplicar el filtro
    this.dataSource.paginator = this.paginator;
  }
  abrirPrimeraVezForm(jokeId: number) {
    this.router.navigate(['/primeravez/nuevo', jokeId]); 
  }
  
}
