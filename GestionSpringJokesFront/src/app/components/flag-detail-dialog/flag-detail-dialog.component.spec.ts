import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlagDetailDialogComponent } from './flag-detail-dialog.component';

describe('FlagDetailDialogComponent', () => {
  let component: FlagDetailDialogComponent;
  let fixture: ComponentFixture<FlagDetailDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FlagDetailDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FlagDetailDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
