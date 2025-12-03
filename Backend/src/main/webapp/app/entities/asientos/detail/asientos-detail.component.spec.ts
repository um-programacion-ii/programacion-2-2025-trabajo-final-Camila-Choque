import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AsientosDetailComponent } from './asientos-detail.component';

describe('Asientos Management Detail Component', () => {
  let comp: AsientosDetailComponent;
  let fixture: ComponentFixture<AsientosDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AsientosDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./asientos-detail.component').then(m => m.AsientosDetailComponent),
              resolve: { asientos: () => of({ id: 30058 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AsientosDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AsientosDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load asientos on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AsientosDetailComponent);

      // THEN
      expect(instance.asientos()).toEqual(expect.objectContaining({ id: 30058 }));
    });
  });

  describe('PreviousState', () => {
    it('should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
