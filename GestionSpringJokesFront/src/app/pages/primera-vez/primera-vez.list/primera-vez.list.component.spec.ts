import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrimeraVezListComponent } from './primera-vez.list.component';

describe('PrimeraVezListComponent', () => {
  let component: PrimeraVezListComponent;
  let fixture: ComponentFixture<PrimeraVezListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrimeraVezListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrimeraVezListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
