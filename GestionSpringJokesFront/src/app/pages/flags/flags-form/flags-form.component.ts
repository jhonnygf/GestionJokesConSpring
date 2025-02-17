import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Flags } from 'src/app/models/flags.model';
import { FlagService } from 'src/app/services/flag.service';

@Component({
  selector: 'app-flags-form',
  templateUrl: './flags-form.component.html',
  styleUrls: ['./flags-form.component.css'],
})
export class FlagsFormComponent implements OnInit {
  flagForm!: FormGroup;
  isEditMode = false;
  loading = false;
  flagId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router,
    private flagService: FlagService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // Detecta si se pasó un ID en la ruta (modo edición)
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam && !isNaN(Number(idParam))) {
      this.flagId = Number(idParam);
      this.isEditMode = true;
    }
    // Crea el formulario según el modo
    this.initForm();

    if (this.isEditMode) {
      this.loadFlagData(this.flagId!);
    }
  }

  initForm() {
    if (this.isEditMode) {
      // En modo edición se incluye el control de ID (deshabilitado)
      this.flagForm = this.fb.group({
        id: [{ value: null, disabled: true }, [Validators.required]],
        flag: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
      });
    } else {
      // En modo creación NO se incluye el control de ID
      this.flagForm = this.fb.group({
        flag: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
      });
    }
  }

  loadFlagData(id: number): void {
    this.flagService.getFlag(id).subscribe({
      next: (flag) => {
        this.flagForm.patchValue({
          id: flag.id,
          flag: flag.flag
        });
      },
      error: () => {
        this.snackBar.open('Error al cargar la flag.', 'Cerrar', { duration: 3000 });
      }
    });
  }

  guardarFlag(): void {
    if (this.flagForm.invalid) {
      this.snackBar.open('Por favor revisa el formulario.', 'Cerrar', { duration: 3000 });
      return;
    }
  
    this.loading = true;
  
    if (this.isEditMode) {
      const flagData: Flags = {
        id: this.flagId!,
        flag: this.flagForm.value.flag.trim().toLowerCase()
      };
      this.flagService.updateFlag(flagData.id!, flagData).subscribe({
        next: () => {
          this.snackBar.open('¡Flag actualizada con éxito!', 'Cerrar', { duration: 3000 });
          this.router.navigate(['/flags']);
        },
        error: (error) => {
          this.loading = false;
          console.error('Error al actualizar flag:', error);
          this.snackBar.open('Error al actualizar la flag.', 'Cerrar', { duration: 3000 });
        }
      });
    } else {
      // En modo creación no se envía el ID, se deja que el backend lo genere
      const flagData: Flags = {
        flag: this.flagForm.value.flag.trim().toLowerCase()
      };
      this.flagService.createFlag(flagData).subscribe({
        next: () => {
          this.snackBar.open('¡Flag creada con éxito!', 'Cerrar', { duration: 3000 });
          this.router.navigate(['/flags']);
        },
        error: (error) => {
          this.loading = false;
          console.error('Error al crear flag:', error);
          this.snackBar.open('Error al crear la flag. Inténtalo de nuevo.', 'Cerrar', { duration: 3000 });
        }
      });
    }
  }
}
