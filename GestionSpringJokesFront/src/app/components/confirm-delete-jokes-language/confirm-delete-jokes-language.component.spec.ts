import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmDeleteJokesLanguageComponent } from './confirm-delete-jokes-language.component';

describe('ConfirmDeleteJokesLanguageComponent', () => {
  let component: ConfirmDeleteJokesLanguageComponent;
  let fixture: ComponentFixture<ConfirmDeleteJokesLanguageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfirmDeleteJokesLanguageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConfirmDeleteJokesLanguageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
