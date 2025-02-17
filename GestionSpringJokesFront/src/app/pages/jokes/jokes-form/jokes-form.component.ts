import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { JokeService } from '../../../services/joke.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-joke-form',
  templateUrl: './jokes-form.component.html',
  styleUrls: ['./jokes-form.component.css'],
})
/**
 *  JokeFormComponent es el formulario de creación y edición de jokes, si el usuario
 *  accede a él para crear tendrá los campos vacíos y deberá rellenarlos todos menos
 *  text2 para poder crear un nuevo joke, si introduce información en text1 y text2 
 * creara un joke de typ2 2 o twoparts y si solo introduce información en text1 creará
 *  un joke de type 1 o single. Las flags no serán obligatorias por lo que podrá introduci
 *  una, ninguna o varías. Si el usuario accede a este formulario para editar saldrám los
 *  datos predefinidos que ya tiene el joke.
 */
export class JokeFormComponent implements OnInit {
  jokeForm!: FormGroup;
  isEditMode = false; // Cambia a true si este formulario es para editar un chiste
  loading = false; // Controla el spinner mientras se guarda el chiste

  categories: any[] = [];
  languages: any[] = [];
  flags: any[] = [];
  dataLoaded = false; // Controla si los datos ya fueron cargados

  constructor(
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router,
    private jokesService: JokeService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.initForm();
    this.loadCategories();
    this.loadLanguages();
    this.loadFlags();

    const jokeId = this.route.snapshot.paramMap.get('id'); // Obtén el ID de la ruta
    if (jokeId) {
      this.isEditMode = true;
      this.loadJokeData(Number(jokeId)); // Carga los datos del chiste
    }
  }

  // Cargar categorías desde el backend
  loadCategories(): void {
    this.jokesService.getCategories().subscribe({
      next: (categories) => {
        this.categories = categories.map(cat => ({
          id: cat.id,
          name: cat.category 
        }));
      },
      error: (err) => {
        console.error('Error al cargar categorías:', err);
      }
    });
  }

  // Cargar idiomas desde el backend
  loadLanguages(): void {
    this.jokesService.getLanguages().subscribe({
      next: (languages) => {
        this.languages = languages.map(lang => ({
          id: lang.id,
          code: lang.code,
          name: lang.language 
        }));
      },
      error: (err) => {
        console.error('Error al cargar idiomas:', err);
      }
    });
  }

  // Cargar flags desde el backend 
  loadFlags(): void {
    this.jokesService.getFlags().subscribe({
      next: (flags) => {
        this.flags = flags.map(flag => ({
          id: flag.id,
          name: flag.flag 
        }));
        this.initFlagsArray();
      },
      error: (err) => {
        console.error('Error al cargar flags:', err);
      }
    });
  }


  initForm() {
    this.jokeForm = this.fb.group({
      text1: ['', Validators.required],
      text2: [''],
      category: [null, Validators.required],
      language: [null, Validators.required],
      flags: this.fb.array([]),
    });
  }

  initFlagsArray(): void {
    const flagsArray = this.jokeForm.get('flags') as FormArray;
    flagsArray.clear();

    this.flags.forEach(() => {
      flagsArray.push(this.fb.control(false));
    });
  }

  getSelectedFlags(): number[] {
    const flagsFormArray = this.jokeForm.get('flags') as FormArray;
    if (!flagsFormArray || flagsFormArray.length !== this.flags.length) return [];
    return this.flags
      .map((flag, i) => (flagsFormArray.controls[i]?.value ? flag.id : null))
      .filter((id) => id !== null) as number[];
  }

  guardarJoke(): void {
    if (this.jokeForm.invalid) {
      this.snackBar.open('Por favor revisa el formulario.', 'Cerrar', { duration: 3000 });
      return;
    }
  
    const newJoke = {
      text1: this.jokeForm.value.text1,
      text2: this.jokeForm.value.text2,
      categories: {
        id: this.jokeForm.value.category,
        category: this.categories.find(cat => cat.id === this.jokeForm.value.category)?.name ?? 'Sin categoría'
      },
      language: {
        id: this.jokeForm.value.language,
        language: this.languages.find(lang => lang.id === this.jokeForm.value.language)?.name ?? 'Idioma no especificado'
      },
      flagses: this.getSelectedFlags().map(id => ({
        id,
        flag: this.flags.find(f => f.id === id)?.name ?? 'Desconocido'
      })),
      // 🔹 Determina el tipo de chiste
      types: {
        id: this.jokeForm.value.text2 ? 2 : 1, // Si tiene text2 es "twopart", sino "single"
        type: this.jokeForm.value.text2 ? 'Twopart' : 'Single'
      }
    };
  
    console.log('Datos enviados al backend:', newJoke); // 🔥 Verifica en la consola del navegador
  
    this.jokesService.createJoke(newJoke).subscribe({
      next: (response) => {
        this.snackBar.open('¡Chiste creado con éxito!', 'Cerrar', { duration: 3000 });
        this.router.navigate(['/jokes']);
      },
      error: (error) => {
        console.error('Error al guardar el chiste:', error);
        this.snackBar.open('Error al crear el chiste.', 'Cerrar', { duration: 3000 });
      },
    });
  }
  
  

  loadJokeData(id: number): void {
    this.jokesService.getJoke(id).subscribe({
      next: (joke) => {
        this.jokeForm.patchValue({
          text1: joke.text1,
          text2: joke.text2,
          category: joke.categories?.id,
          language: joke.language?.id,
        });

        this.initFlagsArray();

        const flagsArray = this.jokeForm.get('flags') as FormArray;
        joke.flagses.forEach((flag) => {
          const index = this.flags.findIndex((f) => f.id === flag.id);
          if (index !== -1) {
            flagsArray.controls[index].setValue(true); 
          }
        });
      },
      error: (err) => {
        console.error('Error al cargar el chiste:', err);
        this.snackBar.open('Error al cargar el chiste.', 'Cerrar', { duration: 3000 });
        this.router.navigate(['/jokes']);
      },
    });
  }

}