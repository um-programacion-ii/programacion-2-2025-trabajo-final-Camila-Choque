package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Integrantes;
import com.mycompany.myapp.service.dto.IntegrantesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Integrantes} and its DTO {@link IntegrantesDTO}.
 */
@Mapper(componentModel = "spring")
public interface IntegrantesMapper extends EntityMapper<IntegrantesDTO, Integrantes> {}
