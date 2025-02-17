import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface ConfirmDeleteJokesTypeData {
  typeName: string;
  jokesCount: number;
}

@Component({
  selector: 'app-confirm-delete-jokes-type',
  templateUrl: './confirm-delete-jokes-types.component.html',
  styleUrls: ['./confirm-delete-jokes-types.component.css']
})
export class ConfirmDeleteJokesTypeComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: ConfirmDeleteJokesTypeData) {}
}