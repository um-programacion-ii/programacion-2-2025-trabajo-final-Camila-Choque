import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IntegrantesService } from '../service/integrantes.service';
import { IIntegrantes } from '../integrantes.model';
import { IntegrantesFormService } from './integrantes-form.service';

import { IntegrantesUpdateComponent } from './integrantes-update.component';

describe('Integrantes Management Update Component', () => {
  let comp: IntegrantesUpdateComponent;
  let fixture: ComponentFixture<IntegrantesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let integrantesFormService: IntegrantesFormService;
  let integrantesService: IntegrantesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [IntegrantesUpdateComponent],
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
      .overrideTemplate(IntegrantesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IntegrantesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    integrantesFormService = TestBed.inject(IntegrantesFormService);
    integrantesService = TestBed.inject(IntegrantesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const integrantes: IIntegrantes = { id: 4905 };

      activatedRoute.data = of({ integrantes });
      comp.ngOnInit();

      expect(comp.integrantes).toEqual(integrantes);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIntegrantes>>();
      const integrantes = { id: 28294 };
      jest.spyOn(integrantesFormService, 'getIntegrantes').mockReturnValue(integrantes);
      jest.spyOn(integrantesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ integrantes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: integrantes }));
      saveSubject.complete();

      // THEN
      expect(integrantesFormService.getIntegrantes).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(integrantesService.update).toHaveBeenCalledWith(expect.objectContaining(integrantes));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIntegrantes>>();
      const integrantes = { id: 28294 };
      jest.spyOn(integrantesFormService, 'getIntegrantes').mockReturnValue({ id: null });
      jest.spyOn(integrantesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ integrantes: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: integrantes }));
      saveSubject.complete();

      // THEN
      expect(integrantesFormService.getIntegrantes).toHaveBeenCalled();
      expect(integrantesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIntegrantes>>();
      const integrantes = { id: 28294 };
      jest.spyOn(integrantesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ integrantes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(integrantesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
