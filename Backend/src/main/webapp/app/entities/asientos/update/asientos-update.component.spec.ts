import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { AsientosService } from '../service/asientos.service';
import { IAsientos } from '../asientos.model';
import { AsientosFormService } from './asientos-form.service';

import { AsientosUpdateComponent } from './asientos-update.component';

describe('Asientos Management Update Component', () => {
  let comp: AsientosUpdateComponent;
  let fixture: ComponentFixture<AsientosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let asientosFormService: AsientosFormService;
  let asientosService: AsientosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AsientosUpdateComponent],
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
      .overrideTemplate(AsientosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AsientosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    asientosFormService = TestBed.inject(AsientosFormService);
    asientosService = TestBed.inject(AsientosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const asientos: IAsientos = { id: 12575 };

      activatedRoute.data = of({ asientos });
      comp.ngOnInit();

      expect(comp.asientos).toEqual(asientos);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAsientos>>();
      const asientos = { id: 30058 };
      jest.spyOn(asientosFormService, 'getAsientos').mockReturnValue(asientos);
      jest.spyOn(asientosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ asientos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: asientos }));
      saveSubject.complete();

      // THEN
      expect(asientosFormService.getAsientos).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(asientosService.update).toHaveBeenCalledWith(expect.objectContaining(asientos));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAsientos>>();
      const asientos = { id: 30058 };
      jest.spyOn(asientosFormService, 'getAsientos').mockReturnValue({ id: null });
      jest.spyOn(asientosService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ asientos: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: asientos }));
      saveSubject.complete();

      // THEN
      expect(asientosFormService.getAsientos).toHaveBeenCalled();
      expect(asientosService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAsientos>>();
      const asientos = { id: 30058 };
      jest.spyOn(asientosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ asientos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(asientosService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
