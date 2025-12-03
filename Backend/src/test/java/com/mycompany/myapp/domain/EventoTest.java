package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EventoTestSamples.*;
import static com.mycompany.myapp.domain.IntegrantesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EventoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Evento.class);
        Evento evento1 = getEventoSample1();
        Evento evento2 = new Evento();
        assertThat(evento1).isNotEqualTo(evento2);

        evento2.setId(evento1.getId());
        assertThat(evento1).isEqualTo(evento2);

        evento2 = getEventoSample2();
        assertThat(evento1).isNotEqualTo(evento2);
    }

    @Test
    void integrantesTest() {
        Evento evento = getEventoRandomSampleGenerator();
        Integrantes integrantesBack = getIntegrantesRandomSampleGenerator();

        evento.addIntegrantes(integrantesBack);
        assertThat(evento.getIntegrantes()).containsOnly(integrantesBack);
        assertThat(integrantesBack.getEvento()).isEqualTo(evento);

        evento.removeIntegrantes(integrantesBack);
        assertThat(evento.getIntegrantes()).doesNotContain(integrantesBack);
        assertThat(integrantesBack.getEvento()).isNull();

        evento.integrantes(new HashSet<>(Set.of(integrantesBack)));
        assertThat(evento.getIntegrantes()).containsOnly(integrantesBack);
        assertThat(integrantesBack.getEvento()).isEqualTo(evento);

        evento.setIntegrantes(new HashSet<>());
        assertThat(evento.getIntegrantes()).doesNotContain(integrantesBack);
        assertThat(integrantesBack.getEvento()).isNull();
    }
}
