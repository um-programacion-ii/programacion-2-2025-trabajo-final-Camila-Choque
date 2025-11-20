package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Evento;
import com.mycompany.myapp.domain.Integrantes;
import com.mycompany.myapp.service.dto.EventoDTO;
import com.mycompany.myapp.service.dto.IntegrantesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Integrantes} and its DTO {@link IntegrantesDTO}.
 */
@Mapper(componentModel = "spring")
public interface IntegrantesMapper extends EntityMapper<IntegrantesDTO, Integrantes> {
    @Mapping(target = "evento", source = "evento", qualifiedByName = "eventoId")
    IntegrantesDTO toDto(Integrantes s);

    @Named("eventoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventoDTO toDtoEventoId(Evento evento);
}
