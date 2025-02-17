import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmDeleteJokesCategoryComponent } from './confirm-delete-jokes-category.component';

describe('ConfirmDeleteJokesCategoryComponent', () => {
  let component: ConfirmDeleteJokesCategoryComponent;
  let fixture: ComponentFixture<ConfirmDeleteJokesCategoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfirmDeleteJokesCategoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConfirmDeleteJokesCategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
