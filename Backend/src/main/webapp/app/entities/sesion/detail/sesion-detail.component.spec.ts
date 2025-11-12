import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SesionDetailComponent } from './sesion-detail.component';

describe('Sesion Management Detail Component', () => {
  let comp: SesionDetailComponent;
  let fixture: ComponentFixture<SesionDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SesionDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./sesion-detail.component').then(m => m.SesionDetailComponent),
              resolve: { sesion: () => of({ id: 6272 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SesionDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SesionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load sesion on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SesionDetailComponent);

      // THEN
      expect(instance.sesion()).toEqual(expect.objectContaining({ id: 6272 }));
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
