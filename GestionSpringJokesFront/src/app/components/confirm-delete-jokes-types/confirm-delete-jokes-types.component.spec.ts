import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmDeleteJokesTypeComponent } from './confirm-delete-jokes-types.component';

describe('ConfirmDeleteJokesTypesComponent', () => {
  let component: ConfirmDeleteJokesTypeComponent;
  let fixture: ComponentFixture<ConfirmDeleteJokesTypeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfirmDeleteJokesTypeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConfirmDeleteJokesTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
