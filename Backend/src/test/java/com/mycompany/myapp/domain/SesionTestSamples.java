package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SesionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sesion getSesionSample1() {
        return new Sesion().id(1L).token("token1").estadoFlujo("estadoFlujo1").eventoSeleccionado(1L);
    }

    public static Sesion getSesionSample2() {
        return new Sesion().id(2L).token("token2").estadoFlujo("estadoFlujo2").eventoSeleccionado(2L);
    }

    public static Sesion getSesionRandomSampleGenerator() {
        return new Sesion()
            .id(longCount.incrementAndGet())
            .token(UUID.randomUUID().toString())
            .estadoFlujo(UUID.randomUUID().toString())
            .eventoSeleccionado(longCount.incrementAndGet());
    }
}
