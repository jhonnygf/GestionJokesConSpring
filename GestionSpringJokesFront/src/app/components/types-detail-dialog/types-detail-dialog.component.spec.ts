import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TypeDetailDialogComponent } from './types-detail-dialog.component';


describe('TypesDetailDialogComponent', () => {
  let component: TypeDetailDialogComponent;
  let fixture: ComponentFixture<TypeDetailDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TypeDetailDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TypeDetailDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
