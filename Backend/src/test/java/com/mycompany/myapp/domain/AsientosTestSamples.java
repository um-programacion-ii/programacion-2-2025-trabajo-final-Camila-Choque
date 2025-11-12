package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AsientosTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Asientos getAsientosSample1() {
        return new Asientos().id(1L).fila(1).columna(1).persona("persona1").estado("estado1");
    }

    public static Asientos getAsientosSample2() {
        return new Asientos().id(2L).fila(2).columna(2).persona("persona2").estado("estado2");
    }

    public static Asientos getAsientosRandomSampleGenerator() {
        return new Asientos()
            .id(longCount.incrementAndGet())
            .fila(intCount.incrementAndGet())
            .columna(intCount.incrementAndGet())
            .persona(UUID.randomUUID().toString())
            .estado(UUID.randomUUID().toString());
    }
}
