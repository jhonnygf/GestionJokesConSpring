import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Types } from 'src/app/models/types.model';
import { TypeService } from 'src/app/services/type.service';

@Component({
  selector: 'app-types-form',
  templateUrl: './types-form.component.html',
  styleUrls: ['./types-form.component.css']
})
export class TypesFormComponent implements OnInit {
  typeForm!: FormGroup;
  isEditMode = false;
  loading = false;
  typeId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router,
    private typeService: TypeService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    // Detectar si se pasó un ID en la ruta (modo edición)
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam && !isNaN(Number(idParam))) {
      this.typeId = Number(idParam);
      this.isEditMode = true;
    }
    // Inicializar el formulario según el modo
    this.initForm();

    if (this.isEditMode) {
      this.loadTypeData(this.typeId!);
    }
  }

  initForm(): void {
    if (this.isEditMode) {
      // En modo edición se incluye el control 'id' (deshabilitado)
      this.typeForm = this.fb.group({
        id: [{ value: null, disabled: true }, [Validators.required]],
        type: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]]
      });
    } else {
      // En modo creación, solo se pide el nombre del type
      this.typeForm = this.fb.group({
        type: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]]
      });
    }
  }

  loadTypeData(id: number): void {
    this.typeService.getType(id).subscribe({
      next: (typeData) => {
        this.typeForm.patchValue({
          id: typeData.id,
          type: typeData.type
        });
      },
      error: () => {
        this.snackBar.open('Error al cargar el type.', 'Cerrar', { duration: 3000 });
      }
    });
  }

  guardarType(): void {
    if (this.typeForm.invalid) {
      this.snackBar.open('Por favor, revisa el formulario.', 'Cerrar', { duration: 3000 });
      return;
    }

    this.loading = true;

    if (this.isEditMode) {
      // En modo edición, se utiliza el ID de la ruta
      const typeData: Types = {
        id: this.typeId!,
        type: this.typeForm.value.type.trim()
      };
      this.typeService.updateType(typeData.id!, typeData).subscribe({
        next: () => {
          this.snackBar.open('¡Type actualizado con éxito!', 'Cerrar', { duration: 3000 });
          this.router.navigate(['/types']);
        },
        error: (error) => {
          this.loading = false;
          console.error('Error al actualizar type:', error);
          this.snackBar.open('Error al actualizar el type.', 'Cerrar', { duration: 3000 });
        }
      });
    } else {
      // En modo creación, se deja que el backend genere el ID
      const typeData: Types = {
        type: this.typeForm.value.type.trim()
      };
      this.typeService.createType(typeData).subscribe({
        next: () => {
          this.snackBar.open('¡Type creado con éxito!', 'Cerrar', { duration: 3000 });
          this.router.navigate(['/types']);
        },
        error: (error) => {
          this.loading = false;
          console.error('Error al crear type:', error);
          this.snackBar.open('Error al crear el type. Inténtalo de nuevo.', 'Cerrar', { duration: 3000 });
        }
      });
    }
  }
}
