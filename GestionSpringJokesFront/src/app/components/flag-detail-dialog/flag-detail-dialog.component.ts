import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface FlagDetailData {
  id: number;
  flag: string;
  jokes: string[]; // o el tipo de dato que uses para los chistes asociados
}

@Component({
  selector: 'app-flag-detail-dialog',
  templateUrl: './flag-detail-dialog.component.html',
  styleUrls: ['./flag-detail-dialog.component.css']
})
export class FlagDetailDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: FlagDetailData) {}
}
