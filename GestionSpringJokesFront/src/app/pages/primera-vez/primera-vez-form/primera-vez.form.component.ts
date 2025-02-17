import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { JokeService } from 'src/app/services/joke.service';
import { PrimeraVezService } from 'src/app/services/primera-vez.service';


@Component({
  selector: 'app-primera-vez',
  templateUrl: './primera-vez.form.component.html',
  styleUrls: ['./primera-vez.form.component.css'],
})
export class PrimeraVezFormComponent implements OnInit {
  primeraVezForm: FormGroup;
  jokes: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private pvService: PrimeraVezService,
    private jokesService: JokeService
  ) {
    this.primeraVezForm = this.fb.group({
      programa: ['', Validators.required],
      fechaEmision: ['', Validators.required],
      jokeId: [{ value: null, disabled: true }, Validators.required], // Deshabilitado
      telefonos: this.fb.array([this.fb.control('', Validators.required)])
    });
  }
  
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const jokeId = params['jokeId'];
      console.log('JokeId recibido:', jokeId);
  
      if (jokeId && !isNaN(Number(jokeId))) {
        this.primeraVezForm.patchValue({ jokeId: Number(jokeId) }); // 🔥 Convertir a número
      } else {
        console.error("Error: No se recibió un jokeId válido.");
      }
    });
  }
  
  
  

  // Getter para acceder al array de teléfonos
  get telefonos(): FormArray {
    return this.primeraVezForm.get('telefonos') as FormArray;
  }

  addTelefono(): void {
    this.telefonos.push(this.fb.control('', Validators.required));
  }

  removeTelefono(index: number): void {
    if (this.telefonos.length > 1) {
      this.telefonos.removeAt(index);
    }
  }

  loadJokes(): void {
    // Suponiendo que el servicio de jokes tiene un método getAll()
    this.jokesService.getJokes().subscribe(data => {
      this.jokes = data;
    });
  }

  onSubmit(): void {
    if (this.primeraVezForm.invalid) {
      return;
    }
  
    // Obtiene TODOS los valores, incluso los de controles deshabilitados
    const formValue = this.primeraVezForm.getRawValue();
  
    // Verifica
    console.log('Valor bruto del formulario:', formValue);
  
    if (!formValue.jokeId || isNaN(Number(formValue.jokeId))) {
      console.error("Error: jokeId es inválido:", formValue.jokeId);
      return;
    }
  
    const nuevaPrimeraVez = {
      programa: formValue.programa,
      fechaEmision: formValue.fechaEmision,
      joke: { id: Number(formValue.jokeId) },
      telefonos: formValue.telefonos.map((numero: string) => ({ numero }))
    };
  
    console.log('JSON enviado al backend:', JSON.stringify(nuevaPrimeraVez));
  
    this.pvService.create(nuevaPrimeraVez).subscribe(
      response => {
        console.log('Registro creado exitosamente', response);
      },
      error => {
        console.error('Error al crear el registro', error.error);
      }
    );
  }
  
  
  
}

