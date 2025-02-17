import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PrimeraVezFormComponent } from './primera-vez.form.component';



describe('PrimeraVezComponent', () => {
  let component: PrimeraVezFormComponent;
  let fixture: ComponentFixture<PrimeraVezFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrimeraVezFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrimeraVezFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
