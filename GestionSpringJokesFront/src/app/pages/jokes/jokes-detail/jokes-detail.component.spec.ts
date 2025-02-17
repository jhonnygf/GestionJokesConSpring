import { ComponentFixture, TestBed } from '@angular/core/testing';
import { JokeDetailComponent } from './jokes-detail.component';

describe('JokesDetailComponent', () => {
  let component: JokeDetailComponent;
  let fixture: ComponentFixture<JokeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JokeDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JokeDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
