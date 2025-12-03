import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SesionService } from '../service/sesion.service';
import { ISesion } from '../sesion.model';
import { SesionFormService } from './sesion-form.service';

import { SesionUpdateComponent } from './sesion-update.component';

describe('Sesion Management Update Component', () => {
  let comp: SesionUpdateComponent;
  let fixture: ComponentFixture<SesionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sesionFormService: SesionFormService;
  let sesionService: SesionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SesionUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SesionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SesionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sesionFormService = TestBed.inject(SesionFormService);
    sesionService = TestBed.inject(SesionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const sesion: ISesion = { id: 14634 };

      activatedRoute.data = of({ sesion });
      comp.ngOnInit();

      expect(comp.sesion).toEqual(sesion);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISesion>>();
      const sesion = { id: 6272 };
      jest.spyOn(sesionFormService, 'getSesion').mockReturnValue(sesion);
      jest.spyOn(sesionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sesion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sesion }));
      saveSubject.complete();

      // THEN
      expect(sesionFormService.getSesion).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sesionService.update).toHaveBeenCalledWith(expect.objectContaining(sesion));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISesion>>();
      const sesion = { id: 6272 };
      jest.spyOn(sesionFormService, 'getSesion').mockReturnValue({ id: null });
      jest.spyOn(sesionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sesion: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sesion }));
      saveSubject.complete();

      // THEN
      expect(sesionFormService.getSesion).toHaveBeenCalled();
      expect(sesionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISesion>>();
      const sesion = { id: 6272 };
      jest.spyOn(sesionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sesion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sesionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
