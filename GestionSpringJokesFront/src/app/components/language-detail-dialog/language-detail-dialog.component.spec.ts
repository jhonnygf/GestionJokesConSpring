import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LanguageDetailDialogComponent } from './language-detail-dialog.component';

describe('LanguageDetailDialogComponent', () => {
  let component: LanguageDetailDialogComponent;
  let fixture: ComponentFixture<LanguageDetailDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LanguageDetailDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LanguageDetailDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
