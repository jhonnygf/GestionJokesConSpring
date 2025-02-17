import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { LanguageService } from 'src/app/services/language.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { Language } from 'src/app/models/language.model';
import { ConfirmDeleteLanguageComponent, ConfirmDeleteLanguageData } from 'src/app/components/confirm-delete-jokes-language/confirm-delete-jokes-language.component';
import { LanguageDetailDialogComponent, LanguageDetailData } from 'src/app/components/language-detail-dialog/language-detail-dialog.component';

@Component({
  selector: 'app-languages-list',
  templateUrl: './languages-list.component.html',
  styleUrls: ['./languages-list.component.css'],
})
export class LanguagesListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'language', 'jokeCount', 'acciones'];
  dataSource = new MatTableDataSource<Language>([]);
  filtro = '';
  selectedLanguage: Language | null = null;

  constructor(
    private languageService: LanguageService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadLanguages();
  }

  // Cargar idiomas y calcular el número de chistes asociados
  loadLanguages(): void {
    this.languageService.getLanguages().subscribe({
      next: (languages) => {
        this.dataSource.data = languages.map(language => ({
          ...language,
          jokeCount: language.jokes ? language.jokes.length : 0
        }));
      },
      error: (err) => {
        console.error('Error al cargar los lenguajes:', err);
        this.snackBar.open('Error al cargar los lenguajes.', 'Cerrar', { duration: 3000 });
      }
    });
  }

  aplicarFiltro(): void {
    this.dataSource.filter = this.filtro.trim().toLowerCase();
  }

  // Consultar detalles del idioma
  consultarLanguage(id: number): void {
    console.log("Consultando idioma con ID:", id);
    this.languageService.getLanguage(id).subscribe({
      next: (language) => {
        this.dialog.open(LanguageDetailDialogComponent, {
          width: '500px',
          data: {
            id: language.id,
            language: language.language,
            code: language.code,
            jokes: language.jokes ?? []
          } as LanguageDetailData
        });
      },
      error: (err) => {
        console.error('Error al obtener el idioma:', err);
        this.snackBar.open('Error al obtener la información del idioma.', 'Cerrar', { duration: 3000 });
      }
    });
  }

  // Abre el diálogo de confirmación para eliminar el idioma
  verificarEliminarLanguage(language: Language): void {
    this.selectedLanguage = language;
    const dialogRef = this.dialog.open(ConfirmDeleteLanguageComponent, {
      width: '400px',
      data: {
        languageName: language.language,
        jokesCount: language.jokes ? language.jokes.length : 0
      } as ConfirmDeleteLanguageData
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // Si se confirma, se procede a eliminar el idioma (y sus chistes asociados, según la lógica del backend)
        this.eliminarLanguageConfirmado();
      }
    });
  }

  // Llama al servicio para eliminar el idioma (y sus chistes)
  eliminarLanguageConfirmado(): void {
    if (this.selectedLanguage) {
      this.languageService.deleteLanguage(this.selectedLanguage.id).subscribe({
        next: () => {
          this.snackBar.open('Idioma eliminado con éxito. Se eliminaron todos los chistes asociados.', 'Cerrar', { duration: 3000 });
          this.loadLanguages();
          this.dialog.closeAll();
        },
        error: (err) => {
          console.error('Error al eliminar idioma:', err);
          this.snackBar.open('Error al eliminar idioma.', 'Cerrar', { duration: 3000 });
        }
      });
    }
  }
}
