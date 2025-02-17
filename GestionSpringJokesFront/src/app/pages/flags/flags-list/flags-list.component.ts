import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { FlagService } from 'src/app/services/flag.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Flags } from 'src/app/models/flags.model';
import { ConfirmDeleteDialogComponent } from 'src/app/components/confirm-delete-jokes-flags/confirm-delete-dialog.component';
// Importa el componente de detalle
import { FlagDetailDialogComponent, FlagDetailData } from 'src/app/components/flag-detail-dialog/flag-detail-dialog.component';

@Component({
  selector: 'app-flags-list',
  templateUrl: './flags-list.component.html',
  styleUrls: ['./flags-list.component.css'],
})
export class FlagsListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'flag', 'jokes', 'acciones'];
  dataSource = new MatTableDataSource<Flags>([]);
  filtro = '';
  selectedFlag: Flags | null = null;

  constructor(
    private flagService: FlagService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadFlags();
  }

  loadFlags(): void {
    this.flagService.getFlags().subscribe({
      next: (flags) => {
        this.dataSource.data = flags.map(flag => ({
          ...flag,
          jokeCount: flag.jokes ? flag.jokes.length : 0 // Contamos los chistes
        }));
      },
      error: (err) => {
        console.error('Error al cargar flags:', err);
        this.snackBar.open('Error al cargar flags.', 'Cerrar', { duration: 3000 });
      }
    });
  }

  aplicarFiltro(): void {
    this.dataSource.filter = this.filtro.trim().toLowerCase();
  }

  // Método consultarFlag: obtiene los datos y abre un diálogo de detalle
  consultarFlag(id: number): void {
    this.flagService.getFlag(id).subscribe({
      next: (flag) => {
        this.dialog.open(FlagDetailDialogComponent, {
          width: '500px',
          data: {
            id: flag.id,
            flag: flag.flag,
            jokes: flag.jokes ?? []  // Asegura que sea un array, aunque sea vacío
          } as FlagDetailData
        });
      },
      error: (err) => {
        console.error('Error al obtener la flag:', err);
        this.snackBar.open('Error al obtener la información de la flag.', 'Cerrar', { duration: 3000 });
      }
    });
  }

  verificarEliminarFlag(flag: Flags): void {
    this.selectedFlag = flag;  // Guardamos el flag seleccionado antes de abrir el diálogo
    
    const dialogRef = this.dialog.open(ConfirmDeleteDialogComponent, {
      width: '400px',
      data: { 
        flagName: flag.flag, 
        jokesCount: flag.jokes ? flag.jokes.length : 0
      }
    });
  
    dialogRef.afterClosed().subscribe((result: 'cancelar' | 'desvincular' | 'eliminarConJokes' | 'eliminar') => {
      if (!result || result === 'cancelar') {
        // No se realizó ninguna acción
        return;
      }
      if (result === 'desvincular') {
        // Opción A: Desvincular primero y luego eliminar la flag
        this.desvincularYEliminarFlag();
      } else if (result === 'eliminarConJokes') {
        // Opción B: Eliminar directamente la flag y sus jokes asociados
        this.eliminarFlagYJokes();
      } else if (result === 'eliminar') {
        // Si no hay jokes asociados, eliminar directamente
        this.eliminarFlagConfirmado();
      }
    });
  }
  
  onCancelDelete(): void {
    this.selectedFlag = null;
    this.dialog.closeAll();
  }

  desvincularYEliminarFlag(): void {
    if (this.selectedFlag) {
      this.flagService.desvincularJokes(this.selectedFlag.id!).subscribe({
        next: () => {
          // Luego de desvincular, eliminamos la flag
          this.flagService.deleteFlag(this.selectedFlag!.id!).subscribe({
            next: () => {
              this.snackBar.open('Flag eliminada con éxito.', 'Cerrar', { duration: 3000 });
              this.loadFlags(); // Recargar la lista
              this.dialog.closeAll();
            },
            error: (err) => {
              console.error('Error al eliminar flag:', err);
              this.snackBar.open('Error al eliminar flag.', 'Cerrar', { duration: 3000 });
            }
          });
        },
        error: (err) => {
          console.error('Error al desvincular jokes:', err);
          this.snackBar.open('Error al desvincular jokes.', 'Cerrar', { duration: 3000 });
        }
      });
    }
  }
  
  
  eliminarFlagConfirmado(): void {
    if (this.selectedFlag) {
      this.flagService.deleteFlag(this.selectedFlag.id!).subscribe({
        next: () => {
          this.snackBar.open('Flag eliminada con éxito.', 'Cerrar', { duration: 3000 });
          this.loadFlags(); // Recargar la lista
          this.dialog.closeAll();
        },
        error: (err) => {
          console.error('Error al eliminar flag:', err);
          this.snackBar.open('Error al eliminar flag.', 'Cerrar', { duration: 3000 });
        }
      });
    }
  }
  eliminarFlagYJokes(): void {
    if (this.selectedFlag) {
      this.flagService.deleteFlagAndJokes(this.selectedFlag.id!).subscribe({
        next: () => {
          this.snackBar.open('Flag y sus jokes han sido eliminados con éxito.', 'Cerrar', { duration: 3000 });
          this.loadFlags(); // Recargar la lista
          this.dialog.closeAll();
        },
        error: (err) => {
          console.error('Error al eliminar flag y sus jokes:', err);
          this.snackBar.open('Error al eliminar flag y sus jokes.', 'Cerrar', { duration: 3000 });
        }
      });
    }
  }
  
}

