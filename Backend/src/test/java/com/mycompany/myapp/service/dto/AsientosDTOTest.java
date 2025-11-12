package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AsientosDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AsientosDTO.class);
        AsientosDTO asientosDTO1 = new AsientosDTO();
        asientosDTO1.setId(1L);
        AsientosDTO asientosDTO2 = new AsientosDTO();
        assertThat(asientosDTO1).isNotEqualTo(asientosDTO2);
        asientosDTO2.setId(asientosDTO1.getId());
        assertThat(asientosDTO1).isEqualTo(asientosDTO2);
        asientosDTO2.setId(2L);
        assertThat(asientosDTO1).isNotEqualTo(asientosDTO2);
        asientosDTO1.setId(null);
        assertThat(asientosDTO1).isNotEqualTo(asientosDTO2);
    }
}
