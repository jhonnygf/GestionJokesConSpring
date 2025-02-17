import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

export type DeleteAction = 'cancelar' | 'desvincular' | 'eliminarConJokes' | 'eliminar';

@Component({
  selector: 'app-confirm-delete-dialog',
  templateUrl: './confirm-delete-dialog.component.html',
  styleUrls: ['./confirm-delete-dialog.component.css']
})
export class ConfirmDeleteDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<ConfirmDeleteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { flagName: string; jokesCount: number }
  ) {}

  onCancel(): void {
    this.dialogRef.close('cancelar');
  }

  // Cuando no hay jokes asociados o se desea eliminar sin opción extra
  onConfirm(): void {
    this.dialogRef.close('eliminar');
  }

  // Opción 1: Desvincular primero y luego eliminar la flag
  onDesvincular(): void {
    this.dialogRef.close('desvincular');
  }

  // Opción 2: Eliminar la flag y sus jokes asociados directamente
  onDeleteWithJokes(): void {
    this.dialogRef.close('eliminarConJokes');
  }
}
