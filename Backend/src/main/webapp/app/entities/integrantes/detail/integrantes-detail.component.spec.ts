import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { IntegrantesDetailComponent } from './integrantes-detail.component';

describe('Integrantes Management Detail Component', () => {
  let comp: IntegrantesDetailComponent;
  let fixture: ComponentFixture<IntegrantesDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IntegrantesDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./integrantes-detail.component').then(m => m.IntegrantesDetailComponent),
              resolve: { integrantes: () => of({ id: 28294 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(IntegrantesDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IntegrantesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load integrantes on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', IntegrantesDetailComponent);

      // THEN
      expect(instance.integrantes()).toEqual(expect.objectContaining({ id: 28294 }));
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
