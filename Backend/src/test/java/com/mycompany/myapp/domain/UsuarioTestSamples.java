package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UsuarioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Usuario getUsuarioSample1() {
        return new Usuario()
            .id(1L)
            .username("username1")
            .password("password1")
            .firstName("firstName1")
            .lastName("lastName1")
            .email("email1")
            .nombreAlumno("nombreAlumno1")
            .descripcionProyecto("descripcionProyecto1")
            .jwtToken("jwtToken1")
            .name("name1");
    }

    public static Usuario getUsuarioSample2() {
        return new Usuario()
            .id(2L)
            .username("username2")
            .password("password2")
            .firstName("firstName2")
            .lastName("lastName2")
            .email("email2")
            .nombreAlumno("nombreAlumno2")
            .descripcionProyecto("descripcionProyecto2")
            .jwtToken("jwtToken2")
            .name("name2");
    }

    public static Usuario getUsuarioRandomSampleGenerator() {
        return new Usuario()
            .id(longCount.incrementAndGet())
            .username(UUID.randomUUID().toString())
            .password(UUID.randomUUID().toString())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .nombreAlumno(UUID.randomUUID().toString())
            .descripcionProyecto(UUID.randomUUID().toString())
            .jwtToken(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString());
    }
}
