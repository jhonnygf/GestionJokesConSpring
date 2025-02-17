import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TypesDetailComponent } from './types-detail.component';

describe('TypesDetailComponent', () => {
  let component: TypesDetailComponent;
  let fixture: ComponentFixture<TypesDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TypesDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TypesDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
