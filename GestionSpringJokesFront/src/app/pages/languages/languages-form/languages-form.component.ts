import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Language } from 'src/app/models/language.model';
import { LanguageService } from 'src/app/services/language.service';

@Component({
  selector: 'app-languages-form',
  templateUrl: './languages-form.component.html',
  styleUrls: ['./languages-form.component.css'],
})
export class LanguagesFormComponent implements OnInit {
  languageForm!: FormGroup;
  isEditMode = false;
  loading = false;
  languageId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router,
    private languageService: LanguageService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.initForm();
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam && !isNaN(Number(idParam))) {
      this.languageId = Number(idParam);
      this.isEditMode = true;
      this.loadLanguageData(this.languageId);
    }
  }

  initForm() {
    this.languageForm = this.fb.group({
      id: [{ value: null, disabled: true }],
      language: ['', [Validators.required]],
    });
  }

  loadLanguageData(id: number): void {
    this.languageService.getLanguage(id).subscribe({
      next: (language) => {
        this.languageForm.patchValue({
          id: language.id,
          language: language.language,
        });
      },
      error: () => {
        this.snackBar.open('Error al cargar el idioma.', 'Cerrar', { duration: 3000 });
      }
    });
  }

  guardarLanguage(): void {
    if (this.languageForm.invalid) {
      this.snackBar.open('Por favor revisa el formulario.', 'Cerrar', { duration: 3000 });
      return;
    }

    this.loading = true;
    const languageData: Language = {
      id: this.languageId ? this.languageId : 0,
      language: this.languageForm.value.language.trim(),
    };

    if (this.isEditMode) {
      this.languageService.updateLanguage(languageData.id, languageData).subscribe({
        next: () => {
          this.snackBar.open('¡Idioma actualizado con éxito!', 'Cerrar', { duration: 3000 });
          this.router.navigate(['/languages']);
        },
        error: (error) => {
          this.loading = false;
          console.error('Error al actualizar idioma:', error);
          this.snackBar.open('Error al actualizar el idioma.', 'Cerrar', { duration: 3000 });
        }
      });
    } else {
      this.languageService.createLanguage(languageData).subscribe({
        next: () => {
          this.snackBar.open('¡Idioma creado con éxito!', 'Cerrar', { duration: 3000 });
          this.router.navigate(['/languages']);
        },
        error: (error) => {
          this.loading = false;
          console.error('Error al crear idioma:', error);
          this.snackBar.open('Error al crear el idioma. Inténtalo de nuevo.', 'Cerrar', { duration: 3000 });
        }
      });
    }
  }
}
