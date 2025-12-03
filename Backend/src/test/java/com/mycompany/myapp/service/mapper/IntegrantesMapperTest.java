package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.IntegrantesAsserts.*;
import static com.mycompany.myapp.domain.IntegrantesTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntegrantesMapperTest {

    private IntegrantesMapper integrantesMapper;

    @BeforeEach
    void setUp() {
        integrantesMapper = new IntegrantesMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getIntegrantesSample1();
        var actual = integrantesMapper.toEntity(integrantesMapper.toDto(expected));
        assertIntegrantesAllPropertiesEquals(expected, actual);
    }
}
