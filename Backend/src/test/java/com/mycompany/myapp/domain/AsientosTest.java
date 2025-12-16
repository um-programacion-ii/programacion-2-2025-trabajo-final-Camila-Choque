package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AsientosTestSamples.*;
import static com.mycompany.myapp.domain.EventoTestSamples.*;
import static com.mycompany.myapp.domain.SesionTestSamples.*;
import static com.mycompany.myapp.domain.VentaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AsientosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Asientos.class);
        Asientos asientos1 = getAsientosSample1();
        Asientos asientos2 = new Asientos();
        assertThat(asientos1).isNotEqualTo(asientos2);

        asientos2.setId(asientos1.getId());
        assertThat(asientos1).isEqualTo(asientos2);

        asientos2 = getAsientosSample2();
        assertThat(asientos1).isNotEqualTo(asientos2);
    }

    @Test
    void eventoTest() {
        Asientos asientos = getAsientosRandomSampleGenerator();
        Evento eventoBack = getEventoRandomSampleGenerator();

        asientos.setEvento(eventoBack);
        assertThat(asientos.getEvento()).isEqualTo(eventoBack);

        asientos.evento(null);
        assertThat(asientos.getEvento()).isNull();
    }

    @Test
    void ventaTest() {
        Asientos asientos = getAsientosRandomSampleGenerator();
        Venta ventaBack = getVentaRandomSampleGenerator();

        asientos.setVenta(ventaBack);
        assertThat(asientos.getVenta()).isEqualTo(ventaBack);

        asientos.venta(null);
        assertThat(asientos.getVenta()).isNull();
    }

    @Test
    void sesionTest() {
        Asientos asientos = getAsientosRandomSampleGenerator();
        Sesion sesionBack = getSesionRandomSampleGenerator();

        asientos.setSesion(sesionBack);
        assertThat(asientos.getSesion()).isEqualTo(sesionBack);

        asientos.sesion(null);
        assertThat(asientos.getSesion()).isNull();
    }
}
