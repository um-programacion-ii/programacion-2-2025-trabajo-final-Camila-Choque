package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Sesion;
import com.mycompany.myapp.service.dto.SesionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sesion} and its DTO {@link SesionDTO}.
 */
@Mapper(componentModel = "spring")
public interface SesionMapper extends EntityMapper<SesionDTO, Sesion> {}
