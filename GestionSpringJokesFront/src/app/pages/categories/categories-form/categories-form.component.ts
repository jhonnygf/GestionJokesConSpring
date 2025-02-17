import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CategoryService } from 'src/app/services/category.service';
import { Categories } from 'src/app/models/categories.model';

@Component({
  selector: 'app-categories-form',
  templateUrl: './categories-form.component.html',
  styleUrls: ['./categories-form.component.css'],
})
export class CategoriesFormComponent implements OnInit {
  categoryForm!: FormGroup;
  isEditMode = false;
  loading = false;
  categoryId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router,
    private categoryService: CategoryService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.initForm();

    // Detectar si estamos en modo edición
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam && !isNaN(Number(idParam))) {
      this.categoryId = Number(idParam);
      this.isEditMode = true;
      this.loadCategoryData(this.categoryId);
    }
  }

  initForm() {
    this.categoryForm = this.fb.group({
      id: [{ value: null, disabled: true }], // ✅ ID deshabilitado
      category: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
    });
  }

  loadCategoryData(id: number): void {
    this.categoryService.getCategory(id).subscribe({
      next: (category) => {
        this.categoryForm.patchValue({
          id: category.id, // ✅ Cargar el ID pero mantenerlo deshabilitado
          category: category.category
        });
      },
      error: () => {
        this.snackBar.open('Error al cargar la categoría.', 'Cerrar', { duration: 3000 });
      }
    });
  }

  guardarCategoria(): void {
    if (this.categoryForm.invalid) {
      this.snackBar.open('Por favor revisa el formulario.', 'Cerrar', { duration: 3000 });
      return;
    }

    this.loading = true;
    const categoryData: Categories = {
      id: this.categoryId!, // ✅ Mantener el ID original en edición
      category: this.categoryForm.value.category.trim()
    };

    if (this.isEditMode) {
      this.categoryService.updateCategory(categoryData.id!, categoryData).subscribe({
        next: () => {
          this.snackBar.open('¡Categoría actualizada con éxito!', 'Cerrar', { duration: 3000 });
          this.router.navigate(['/categories']);
        },
        error: (error) => {
          this.loading = false;
          console.error('Error al actualizar categoría:', error);
          this.snackBar.open('Error al actualizar la categoría.', 'Cerrar', { duration: 3000 });
        }
      });
    } else {
      // En la creación NO se envía el ID porque se autogenera
      this.categoryService.createCategory({ category: categoryData.category }).subscribe({
        next: () => {
          this.snackBar.open('¡Categoría creada con éxito!', 'Cerrar', { duration: 3000 });
          this.router.navigate(['/categories']);
        },
        error: (error) => {
          this.loading = false;
          console.error('Error al crear categoría:', error);
          this.snackBar.open('Error al crear la categoría. Inténtalo de nuevo.', 'Cerrar', { duration: 3000 });
        }
      });
    }
  }
}