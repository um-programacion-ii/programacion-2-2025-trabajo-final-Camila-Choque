package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class EventoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Evento getEventoSample1() {
        return new Evento()
            .id(1L)
            .titulo("titulo1")
            .resumen("resumen1")
            .descripcion("descripcion1")
            .direccion("direccion1")
            .imagen("imagen1")
            .filaAsientos(1)
            .columnaAsientos(1)
            .eventoTipoNombre("eventoTipoNombre1")
            .eventoTipoDescripcion("eventoTipoDescripcion1")
            .estado("estado1");
    }

    public static Evento getEventoSample2() {
        return new Evento()
            .id(2L)
            .titulo("titulo2")
            .resumen("resumen2")
            .descripcion("descripcion2")
            .direccion("direccion2")
            .imagen("imagen2")
            .filaAsientos(2)
            .columnaAsientos(2)
            .eventoTipoNombre("eventoTipoNombre2")
            .eventoTipoDescripcion("eventoTipoDescripcion2")
            .estado("estado2");
    }

    public static Evento getEventoRandomSampleGenerator() {
        return new Evento()
            .id(longCount.incrementAndGet())
            .titulo(UUID.randomUUID().toString())
            .resumen(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString())
            .direccion(UUID.randomUUID().toString())
            .imagen(UUID.randomUUID().toString())
            .filaAsientos(intCount.incrementAndGet())
            .columnaAsientos(intCount.incrementAndGet())
            .eventoTipoNombre(UUID.randomUUID().toString())
            .eventoTipoDescripcion(UUID.randomUUID().toString())
            .estado(UUID.randomUUID().toString());
    }
}
