import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { TypeService } from 'src/app/services/type.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Types } from 'src/app/models/types.model';
import { ConfirmDeleteDialogComponent } from 'src/app/components/confirm-delete-jokes-flags/confirm-delete-dialog.component';
import { TypeDetailDialogComponent } from 'src/app/components/types-detail-dialog/types-detail-dialog.component';
import { ConfirmDeleteJokesTypeComponent } from 'src/app/components/confirm-delete-jokes-types/confirm-delete-jokes-types.component';

@Component({
  selector: 'app-types-list',
  templateUrl: './types-list.component.html',
  styleUrls: ['./types-list.component.css']
})
export class TypesListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'type', 'jokeCount', 'acciones'];
  dataSource = new MatTableDataSource<Types>([]);
  filtro = '';
  selectedType: Types | null = null;

  constructor(
    private typeService: TypeService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadTypes();
  }

  loadTypes(): void {
    this.typeService.getTypes().subscribe({
      next: (types) => {
        console.log("Datos recibidos:", types); // Debug para verificar la respuesta
        // Mapeamos cada type y calculamos la propiedad jokeCount a partir de la propiedad jokes
        this.dataSource.data = types.map(type => ({
          ...type,
          jokeCount: type.jokes ? type.jokes.length : 0
        }));
        console.log("Datos con jokeCount:", this.dataSource.data);
      },
      error: (err) => {
        console.error('Error al cargar types:', err);
        this.snackBar.open('Error al cargar types.', 'Cerrar', { duration: 3000 });
      }
    });
  }

  aplicarFiltro(): void {
    this.dataSource.filter = this.filtro.trim().toLowerCase();
  }

  consultarType(id: number): void {
    this.typeService.getType(id).subscribe({
      next: (typeData) => {
        this.dialog.open(TypeDetailDialogComponent, {
          width: '500px',
          data: {
            id: typeData.id,
            type: typeData.type,
            // Se asigna la propiedad "jokeses" a partir de "jokes"
            jokeses: typeData.jokes ? typeData.jokes : []
          }
        });
      },
      error: (err) => {
        console.error('Error al obtener el type:', err);
        this.snackBar.open('Error al obtener la información del type.', 'Cerrar', { duration: 3000 });
      }
    });
  }

  eliminarType(id: number, typeName: string): void {
    this.typeService.getType(id).subscribe({
      next: (typeData) => {
        const jokesCount = typeData.jokes ? typeData.jokes.length : 0;
        const dialogRef = this.dialog.open(ConfirmDeleteJokesTypeComponent, {
          width: '400px',
          data: {
            typeName,
            jokesCount,
          },
        });
        dialogRef.afterClosed().subscribe((confirmed) => {
          if (confirmed) {
            this.typeService.deleteType(id).subscribe(() => {
              this.snackBar.open('Type y chistes eliminados con éxito', 'Cerrar', { duration: 3000 });
              this.loadTypes();
            });
          }
        });
      },
      error: (err) => {
        console.error('Error al obtener el type:', err);
        this.snackBar.open('Error al obtener la información del type.', 'Cerrar', { duration: 3000 });
      }
    });
  }
  
  
}
