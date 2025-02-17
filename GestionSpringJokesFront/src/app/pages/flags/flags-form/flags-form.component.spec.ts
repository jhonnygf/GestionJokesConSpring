import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlagsFormComponent } from './flags-form.component';

describe('FlagsFormComponent', () => {
  let component: FlagsFormComponent;
  let fixture: ComponentFixture<FlagsFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FlagsFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FlagsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
