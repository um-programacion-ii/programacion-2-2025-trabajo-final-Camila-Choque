package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.SesionAsserts.*;
import static com.mycompany.myapp.domain.SesionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SesionMapperTest {

    private SesionMapper sesionMapper;

    @BeforeEach
    void setUp() {
        sesionMapper = new SesionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSesionSample1();
        var actual = sesionMapper.toEntity(sesionMapper.toDto(expected));
        assertSesionAllPropertiesEquals(expected, actual);
    }
}
