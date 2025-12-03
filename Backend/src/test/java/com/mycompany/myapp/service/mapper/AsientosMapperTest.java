package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.AsientosAsserts.*;
import static com.mycompany.myapp.domain.AsientosTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AsientosMapperTest {

    private AsientosMapper asientosMapper;

    @BeforeEach
    void setUp() {
        asientosMapper = new AsientosMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAsientosSample1();
        var actual = asientosMapper.toEntity(asientosMapper.toDto(expected));
        assertAsientosAllPropertiesEquals(expected, actual);
    }
}
