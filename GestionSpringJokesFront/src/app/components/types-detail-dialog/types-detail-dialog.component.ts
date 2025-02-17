import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface TypeDetailData {
  id: number;
  type: string;
  jokeses: string[];
}

@Component({
  selector: 'app-type-detail-dialog',
  templateUrl: './types-detail-dialog.component.html',
  styleUrls: ['./types-detail-dialog.component.css']
})
export class TypeDetailDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: TypeDetailData) {}
}
