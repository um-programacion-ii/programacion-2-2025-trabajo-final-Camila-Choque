package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IntegrantesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Integrantes getIntegrantesSample1() {
        return new Integrantes().id(1L).nombre("nombre1").apellido("apellido1").identificacion("identificacion1");
    }

    public static Integrantes getIntegrantesSample2() {
        return new Integrantes().id(2L).nombre("nombre2").apellido("apellido2").identificacion("identificacion2");
    }

    public static Integrantes getIntegrantesRandomSampleGenerator() {
        return new Integrantes()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .apellido(UUID.randomUUID().toString())
            .identificacion(UUID.randomUUID().toString());
    }
}
