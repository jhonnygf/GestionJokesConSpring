import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface LanguageDetailData {
  id: number;
  language: string;
  code?: string;
  jokes: string[];  // Lista de chistes asociados
}

@Component({
  selector: 'app-language-detail-dialog',
  templateUrl: './language-detail-dialog.component.html',
  styleUrls: ['./language-detail-dialog.component.css']
})
export class LanguageDetailDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: LanguageDetailData) { }
}
