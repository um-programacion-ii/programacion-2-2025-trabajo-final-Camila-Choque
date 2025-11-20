import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IEvento } from 'app/entities/evento/evento.model';
import { EventoService } from 'app/entities/evento/service/evento.service';
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
  let eventoService: EventoService;

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
    eventoService = TestBed.inject(EventoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Evento query and add missing value', () => {
      const integrantes: IIntegrantes = { id: 4905 };
      const evento: IEvento = { id: 11280 };
      integrantes.evento = evento;

      const eventoCollection: IEvento[] = [{ id: 11280 }];
      jest.spyOn(eventoService, 'query').mockReturnValue(of(new HttpResponse({ body: eventoCollection })));
      const additionalEventos = [evento];
      const expectedCollection: IEvento[] = [...additionalEventos, ...eventoCollection];
      jest.spyOn(eventoService, 'addEventoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ integrantes });
      comp.ngOnInit();

      expect(eventoService.query).toHaveBeenCalled();
      expect(eventoService.addEventoToCollectionIfMissing).toHaveBeenCalledWith(
        eventoCollection,
        ...additionalEventos.map(expect.objectContaining),
      );
      expect(comp.eventosSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const integrantes: IIntegrantes = { id: 4905 };
      const evento: IEvento = { id: 11280 };
      integrantes.evento = evento;

      activatedRoute.data = of({ integrantes });
      comp.ngOnInit();

      expect(comp.eventosSharedCollection).toContainEqual(evento);
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

  describe('Compare relationships', () => {
    describe('compareEvento', () => {
      it('should forward to eventoService', () => {
        const entity = { id: 11280 };
        const entity2 = { id: 12252 };
        jest.spyOn(eventoService, 'compareEvento');
        comp.compareEvento(entity, entity2);
        expect(eventoService.compareEvento).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
