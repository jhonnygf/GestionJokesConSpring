import { Component, OnInit } from '@angular/core';
import { PrimeraVez, PrimeraVezService } from 'src/app/services/primera-vez.service';


@Component({
  selector: 'app-primera-vez-list',
  templateUrl: './primera-vez.list.component.html'
})
export class PrimeraVezListComponent implements OnInit {
  primeraVezList: PrimeraVez[] = [];
  editMode: boolean = false;
  selectedPrimeraVez: PrimeraVez | null = null;

  constructor(private pvService: PrimeraVezService) {}

  ngOnInit(): void {
    this.loadPrimeraVez();
  }

  loadPrimeraVez(): void {
    this.pvService.getAll().subscribe(data => {
      this.primeraVezList = data;
    });
  }

  deleteRegistro(id: number): void {
    if (confirm('¿Estás seguro de eliminar este registro junto con sus teléfonos?')) {
      this.pvService.delete(id).subscribe(() => {
        this.loadPrimeraVez();
      });
    }
  }

  editRegistro(pv: PrimeraVez): void {
    // Realiza una copia profunda usando JSON
    this.selectedPrimeraVez = JSON.parse(JSON.stringify(pv));
    this.editMode = true;
  }
  

  saveEdit(): void {
    if (this.selectedPrimeraVez) {
      // Filtrar los teléfonos que tengan valor no vacío
      this.selectedPrimeraVez.telefonos = this.selectedPrimeraVez.telefonos.filter(tel => tel.numero.trim() !== '');
      this.pvService.update(this.selectedPrimeraVez.id, this.selectedPrimeraVez).subscribe(() => {
        alert('Registro actualizado con éxito');
        this.editMode = false;
        this.selectedPrimeraVez = null;
        this.loadPrimeraVez();
      });
    }
  }
  

  removeTelefono(index: number): void {
    if (this.selectedPrimeraVez && this.selectedPrimeraVez.telefonos.length > index) {
      this.selectedPrimeraVez.telefonos.splice(index, 1);
    }
  }

  addTelefono(): void {
    if (this.selectedPrimeraVez) {
      // Agrega un nuevo objeto Telefono con un string vacío
      this.selectedPrimeraVez.telefonos.push({ numero: '' });
    }
  }

  cancelEdit(): void {
    this.editMode = false;
    this.selectedPrimeraVez = null;
  }


}
