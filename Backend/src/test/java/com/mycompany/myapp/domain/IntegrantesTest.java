package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.IntegrantesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntegrantesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Integrantes.class);
        Integrantes integrantes1 = getIntegrantesSample1();
        Integrantes integrantes2 = new Integrantes();
        assertThat(integrantes1).isNotEqualTo(integrantes2);

        integrantes2.setId(integrantes1.getId());
        assertThat(integrantes1).isEqualTo(integrantes2);

        integrantes2 = getIntegrantesSample2();
        assertThat(integrantes1).isNotEqualTo(integrantes2);
    }
}
