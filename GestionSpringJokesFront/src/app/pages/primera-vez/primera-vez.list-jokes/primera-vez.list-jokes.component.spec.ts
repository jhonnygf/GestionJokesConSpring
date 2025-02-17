import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrimeraVezListJokesComponent } from './primera-vez.list-jokes.component';

describe('PrimeraVezListJokesComponent', () => {
  let component: PrimeraVezListJokesComponent;
  let fixture: ComponentFixture<PrimeraVezListJokesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrimeraVezListJokesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrimeraVezListJokesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
