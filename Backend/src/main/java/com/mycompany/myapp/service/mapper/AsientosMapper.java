package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Asientos;
import com.mycompany.myapp.service.dto.AsientosDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Asientos} and its DTO {@link AsientosDTO}.
 */
@Mapper(componentModel = "spring")
public interface AsientosMapper extends EntityMapper<AsientosDTO, Asientos> {}
