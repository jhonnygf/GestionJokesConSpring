import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { JokeDto } from 'src/app/models/jokeDto.model';

@Component({
  selector: 'app-jokes-detail-dialog',
  templateUrl: './jokes-detail-dialog.component.html',
  styleUrls: ['./jokes-detail-dialog.component.css']
})
export class JokesDetailDialogComponent {
  constructor(
    // Con MAT_DIALOG_DATA inyectas los datos que el padre te pase
    @Inject(MAT_DIALOG_DATA) public data: JokeDto
  ) {}
}
