package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AsientosTestSamples.*;
import static com.mycompany.myapp.domain.SesionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SesionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sesion.class);
        Sesion sesion1 = getSesionSample1();
        Sesion sesion2 = new Sesion();
        assertThat(sesion1).isNotEqualTo(sesion2);

        sesion2.setId(sesion1.getId());
        assertThat(sesion1).isEqualTo(sesion2);

        sesion2 = getSesionSample2();
        assertThat(sesion1).isNotEqualTo(sesion2);
    }

    @Test
    void asientosTest() {
        Sesion sesion = getSesionRandomSampleGenerator();
        Asientos asientosBack = getAsientosRandomSampleGenerator();

        sesion.addAsientos(asientosBack);
        assertThat(sesion.getAsientos()).containsOnly(asientosBack);
        assertThat(asientosBack.getSesion()).isEqualTo(sesion);

        sesion.removeAsientos(asientosBack);
        assertThat(sesion.getAsientos()).doesNotContain(asientosBack);
        assertThat(asientosBack.getSesion()).isNull();

        sesion.asientos(new HashSet<>(Set.of(asientosBack)));
        assertThat(sesion.getAsientos()).containsOnly(asientosBack);
        assertThat(asientosBack.getSesion()).isEqualTo(sesion);

        sesion.setAsientos(new HashSet<>());
        assertThat(sesion.getAsientos()).doesNotContain(asientosBack);
        assertThat(asientosBack.getSesion()).isNull();
    }
}
