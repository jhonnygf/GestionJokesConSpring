import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryDetailDialogComponent } from './category-detail-dialog.component';

describe('CategoryDetailDialogComponent', () => {
  let component: CategoryDetailDialogComponent;
  let fixture: ComponentFixture<CategoryDetailDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CategoryDetailDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoryDetailDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
