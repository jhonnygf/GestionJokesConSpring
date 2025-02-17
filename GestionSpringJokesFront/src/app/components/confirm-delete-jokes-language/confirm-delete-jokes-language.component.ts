import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface ConfirmDeleteLanguageData {
  languageName: string;
  jokesCount: number;
}

@Component({
  selector: 'app-confirm-delete-language',
  templateUrl: './confirm-delete-jokes-language.component.html',
  styleUrls: ['./confirm-delete-jokes-language.component.css']
})
export class ConfirmDeleteLanguageComponent {
  constructor(
    public dialogRef: MatDialogRef<ConfirmDeleteLanguageComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ConfirmDeleteLanguageData
  ) {}

  // Método para confirmar la eliminación
  onConfirmDelete(): void {
    this.dialogRef.close(true);
  }

  // Método para cancelar
  onCancel(): void {
    this.dialogRef.close(false);
  }
}
