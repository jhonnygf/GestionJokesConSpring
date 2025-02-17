import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface CategoryDetailData {
  id: number;
  category: string;
  jokeses: string[];
}

@Component({
  selector: 'app-category-detail-dialog',
  templateUrl: './category-detail-dialog.component.html',
  styleUrls: ['./category-detail-dialog.component.css']
})
export class CategoryDetailDialogComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: CategoryDetailData
  ) {}
}