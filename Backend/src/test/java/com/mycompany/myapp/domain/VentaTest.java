package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AsientosTestSamples.*;
import static com.mycompany.myapp.domain.VentaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class VentaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Venta.class);
        Venta venta1 = getVentaSample1();
        Venta venta2 = new Venta();
        assertThat(venta1).isNotEqualTo(venta2);

        venta2.setId(venta1.getId());
        assertThat(venta1).isEqualTo(venta2);

        venta2 = getVentaSample2();
        assertThat(venta1).isNotEqualTo(venta2);
    }

    @Test
    void asientosTest() {
        Venta venta = getVentaRandomSampleGenerator();
        Asientos asientosBack = getAsientosRandomSampleGenerator();

        venta.addAsientos(asientosBack);
        assertThat(venta.getAsientos()).containsOnly(asientosBack);
        assertThat(asientosBack.getVenta()).isEqualTo(venta);

        venta.removeAsientos(asientosBack);
        assertThat(venta.getAsientos()).doesNotContain(asientosBack);
        assertThat(asientosBack.getVenta()).isNull();

        venta.asientos(new HashSet<>(Set.of(asientosBack)));
        assertThat(venta.getAsientos()).containsOnly(asientosBack);
        assertThat(asientosBack.getVenta()).isEqualTo(venta);

        venta.setAsientos(new HashSet<>());
        assertThat(venta.getAsientos()).doesNotContain(asientosBack);
        assertThat(asientosBack.getVenta()).isNull();
    }
}
