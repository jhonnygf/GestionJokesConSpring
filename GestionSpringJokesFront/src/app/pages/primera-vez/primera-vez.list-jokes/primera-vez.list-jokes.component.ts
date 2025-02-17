import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { JokeService } from 'src/app/services/joke.service';
import { Joke } from 'src/app/models/jokes.model';


@Component({
  selector: 'app-primera-vez.list-jokes',
  templateUrl: './primera-vez.list-jokes.component.html',
  styleUrls: ['./primera-vez.list-jokes.component.css']
})
export class PrimeraVezListJokesComponent implements OnInit {
  displayedColumns: string[] = ['id', 'text1', 'text2', 'category', 'language', 'primeraVez'];
  dataSource: MatTableDataSource<Joke> = new MatTableDataSource();
  filtro: string = '';

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private jokesService: JokeService) { }

  ngOnInit(): void {
    this.cargarJokesConPrimeraVez();
  }

  cargarJokesConPrimeraVez(): void {
    // Llama al servicio que obtiene los chistes con primeraVez asociada
    this.jokesService.getJokesConPrimeraVez().subscribe(jokes => {
      this.dataSource = new MatTableDataSource(jokes);
      this.dataSource.paginator = this.paginator;
      // Configurar el filtro (por ejemplo, filtrado por text1, sin distinguir mayúsculas)
      this.dataSource.filterPredicate = (data: Joke, filter: string) => {
        return data.text1.toLowerCase().includes(filter);
      };
    });
  }

  aplicarFiltro(): void {
    const filtroValue = this.filtro.trim().toLowerCase();
    this.dataSource.filter = filtroValue;
  }

  verDetalle(joke: Joke): void {
    // Lógica para ver detalle (puede ser redirigir a una vista de detalle, por ejemplo)
    console.log('Ver detalle de', joke);
  }

  eliminarJoke(id: number): void {
    // Lógica para eliminar el chiste
    if (confirm('¿Está seguro de eliminar este chiste?')) {
      this.jokesService.deleteJoke(id).subscribe(() => {
        this.cargarJokesConPrimeraVez();
      });
    }
  }

  
}
