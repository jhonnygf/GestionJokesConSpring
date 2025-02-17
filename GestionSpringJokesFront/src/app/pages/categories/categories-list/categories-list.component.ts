import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CategoryService } from 'src/app/services/category.service';
import { Categories } from 'src/app/models/categories.model';
import { ConfirmDeleteJokesCategoryComponent } from 'src/app/components/confirm-delete-jokes-category/confirm-delete-jokes-category.component';
import { MatDialog } from '@angular/material/dialog';
import { CategoryDetailDialogComponent } from 'src/app/components/category-detail-dialog/category-detail-dialog.component';

@Component({
  selector: 'app-categories-list',
  templateUrl: './categories-list.component.html',
  styleUrls: ['./categories-list.component.css'],
})
export class CategoriesListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'category', 'jokes', 'acciones'];
  dataSource = new MatTableDataSource<Categories>([]);
  filtro = '';

  constructor(
    private categoryService: CategoryService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.categoryService.getCategories().subscribe({
      next: (categories) => {
        this.dataSource.data = categories.map(category => ({
          ...category,
          // Convertimos "category" a any para poder acceder a "jokes"
          jokeses: (category as any).jokes ?? [],  
          jokesCount: (category as any).jokes ? (category as any).jokes.length : 0
        }));
      },
      error: (err) => {
        console.error('Error al cargar categorías:', err);
        this.snackBar.open('Error al cargar categorías.', 'Cerrar', { duration: 3000 });
      }
    });
  }
  
  
  
  

  aplicarFiltro(): void {
    this.dataSource.filter = this.filtro.trim().toLowerCase();
  }

  consultarCategory(id: number): void {
    this.categoryService.getCategory(id).subscribe({
      next: (category) => {
        this.dialog.open(CategoryDetailDialogComponent, {
          width: '500px',
          data: {
            id: category.id,
            category: category.category,
            // Accedemos a "jokes" convertida a any y la asignamos a "jokeses"
            jokeses: (category as any).jokes ?? []
          }
        });
      },
      error: (err) => {
        console.error('Error al obtener la categoría:', err);
        this.snackBar.open('Error al obtener la información de la categoría.', 'Cerrar', { duration: 3000 });
      }
    });
  }
  
  

  eliminarCategoria(id: number, categoryName: string): void {
    // 1. Pedimos la categoría al backend
    this.categoryService.getCategory(id).subscribe({
      next: (cat) => {
        // 2. Obtenemos el jokesCount (sea en cat.jokesCount o cat.jokeses.length)
        const jokesCount = cat.jokesCount ?? 0; 
        // Si en tu DTO no tienes jokesCount y sí un array: 
        // const jokesCount = cat.jokeses ? cat.jokeses.length : 0;
        
        // 3. Abrimos el diálogo de confirmación
        const dialogRef = this.dialog.open(ConfirmDeleteJokesCategoryComponent, {
          width: '400px',
          data: {
            categoryName,
            jokesCount,
          },
        });
        // 4. Si el usuario confirma, procedemos a borrar
        dialogRef.afterClosed().subscribe((confirmed) => {
          if (confirmed) {
            this.categoryService.deleteCategory(id, true).subscribe(() => {
              this.snackBar.open('Categoría y chistes eliminados con éxito', 'Cerrar', { duration: 3000 });
              this.loadCategories();
            });
          }
        });
      },
      error: (err) => {
        console.error('Error al obtener la categoría:', err);
        this.snackBar.open('Error al obtener la información de la categoría.', 'Cerrar', { duration: 3000 });
      },
    });
  }
  

  
}
