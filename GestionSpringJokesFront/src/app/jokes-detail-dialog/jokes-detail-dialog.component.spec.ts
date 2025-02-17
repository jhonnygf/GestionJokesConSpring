import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JokesDetailDialogComponent } from './jokes-detail-dialog.component';

describe('JokesDetailDialogComponent', () => {
  let component: JokesDetailDialogComponent;
  let fixture: ComponentFixture<JokesDetailDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JokesDetailDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JokesDetailDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
