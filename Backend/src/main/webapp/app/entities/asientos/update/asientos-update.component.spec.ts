import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IEvento } from 'app/entities/evento/evento.model';
import { EventoService } from 'app/entities/evento/service/evento.service';
import { IVenta } from 'app/entities/venta/venta.model';
import { VentaService } from 'app/entities/venta/service/venta.service';
import { ISesion } from 'app/entities/sesion/sesion.model';
import { SesionService } from 'app/entities/sesion/service/sesion.service';
import { IAsientos } from '../asientos.model';
import { AsientosService } from '../service/asientos.service';
import { AsientosFormService } from './asientos-form.service';

import { AsientosUpdateComponent } from './asientos-update.component';

describe('Asientos Management Update Component', () => {
  let comp: AsientosUpdateComponent;
  let fixture: ComponentFixture<AsientosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let asientosFormService: AsientosFormService;
  let asientosService: AsientosService;
  let eventoService: EventoService;
  let ventaService: VentaService;
  let sesionService: SesionService;

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
    eventoService = TestBed.inject(EventoService);
    ventaService = TestBed.inject(VentaService);
    sesionService = TestBed.inject(SesionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Evento query and add missing value', () => {
      const asientos: IAsientos = { id: 12575 };
      const evento: IEvento = { id: 11280 };
      asientos.evento = evento;

      const eventoCollection: IEvento[] = [{ id: 11280 }];
      jest.spyOn(eventoService, 'query').mockReturnValue(of(new HttpResponse({ body: eventoCollection })));
      const additionalEventos = [evento];
      const expectedCollection: IEvento[] = [...additionalEventos, ...eventoCollection];
      jest.spyOn(eventoService, 'addEventoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ asientos });
      comp.ngOnInit();

      expect(eventoService.query).toHaveBeenCalled();
      expect(eventoService.addEventoToCollectionIfMissing).toHaveBeenCalledWith(
        eventoCollection,
        ...additionalEventos.map(expect.objectContaining),
      );
      expect(comp.eventosSharedCollection).toEqual(expectedCollection);
    });

    it('should call Venta query and add missing value', () => {
      const asientos: IAsientos = { id: 12575 };
      const venta: IVenta = { id: 10395 };
      asientos.venta = venta;

      const ventaCollection: IVenta[] = [{ id: 10395 }];
      jest.spyOn(ventaService, 'query').mockReturnValue(of(new HttpResponse({ body: ventaCollection })));
      const additionalVentas = [venta];
      const expectedCollection: IVenta[] = [...additionalVentas, ...ventaCollection];
      jest.spyOn(ventaService, 'addVentaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ asientos });
      comp.ngOnInit();

      expect(ventaService.query).toHaveBeenCalled();
      expect(ventaService.addVentaToCollectionIfMissing).toHaveBeenCalledWith(
        ventaCollection,
        ...additionalVentas.map(expect.objectContaining),
      );
      expect(comp.ventasSharedCollection).toEqual(expectedCollection);
    });

    it('should call Sesion query and add missing value', () => {
      const asientos: IAsientos = { id: 12575 };
      const sesion: ISesion = { id: 6272 };
      asientos.sesion = sesion;

      const sesionCollection: ISesion[] = [{ id: 6272 }];
      jest.spyOn(sesionService, 'query').mockReturnValue(of(new HttpResponse({ body: sesionCollection })));
      const additionalSesions = [sesion];
      const expectedCollection: ISesion[] = [...additionalSesions, ...sesionCollection];
      jest.spyOn(sesionService, 'addSesionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ asientos });
      comp.ngOnInit();

      expect(sesionService.query).toHaveBeenCalled();
      expect(sesionService.addSesionToCollectionIfMissing).toHaveBeenCalledWith(
        sesionCollection,
        ...additionalSesions.map(expect.objectContaining),
      );
      expect(comp.sesionsSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const asientos: IAsientos = { id: 12575 };
      const evento: IEvento = { id: 11280 };
      asientos.evento = evento;
      const venta: IVenta = { id: 10395 };
      asientos.venta = venta;
      const sesion: ISesion = { id: 6272 };
      asientos.sesion = sesion;

      activatedRoute.data = of({ asientos });
      comp.ngOnInit();

      expect(comp.eventosSharedCollection).toContainEqual(evento);
      expect(comp.ventasSharedCollection).toContainEqual(venta);
      expect(comp.sesionsSharedCollection).toContainEqual(sesion);
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

    describe('compareVenta', () => {
      it('should forward to ventaService', () => {
        const entity = { id: 10395 };
        const entity2 = { id: 27589 };
        jest.spyOn(ventaService, 'compareVenta');
        comp.compareVenta(entity, entity2);
        expect(ventaService.compareVenta).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareSesion', () => {
      it('should forward to sesionService', () => {
        const entity = { id: 6272 };
        const entity2 = { id: 14634 };
        jest.spyOn(sesionService, 'compareSesion');
        comp.compareSesion(entity, entity2);
        expect(sesionService.compareSesion).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
