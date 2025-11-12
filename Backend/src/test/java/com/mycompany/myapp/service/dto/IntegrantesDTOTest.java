package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntegrantesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntegrantesDTO.class);
        IntegrantesDTO integrantesDTO1 = new IntegrantesDTO();
        integrantesDTO1.setId(1L);
        IntegrantesDTO integrantesDTO2 = new IntegrantesDTO();
        assertThat(integrantesDTO1).isNotEqualTo(integrantesDTO2);
        integrantesDTO2.setId(integrantesDTO1.getId());
        assertThat(integrantesDTO1).isEqualTo(integrantesDTO2);
        integrantesDTO2.setId(2L);
        assertThat(integrantesDTO1).isNotEqualTo(integrantesDTO2);
        integrantesDTO1.setId(null);
        assertThat(integrantesDTO1).isNotEqualTo(integrantesDTO2);
    }
}
