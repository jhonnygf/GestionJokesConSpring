import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface ConfirmDeleteData {
  categoryName: string;
  jokesCount: number;
}

@Component({
  selector: 'app-confirm-delete-jokes-category',
  templateUrl: './confirm-delete-jokes-category.component.html',
  styleUrls: ['./confirm-delete-jokes-category.component.css'],
})
export class ConfirmDeleteJokesCategoryComponent {
  constructor(
    public dialogRef: MatDialogRef<ConfirmDeleteJokesCategoryComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ConfirmDeleteData
  ) {}

  // Confirmar eliminación
  onConfirmDelete(): void {
    this.dialogRef.close(true);
  }

  // Cancelar
  onCancel(): void {
    this.dialogRef.close(false);
  }
}
