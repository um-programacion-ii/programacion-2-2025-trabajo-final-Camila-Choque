package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Asientos;
import com.mycompany.myapp.domain.Evento;
import com.mycompany.myapp.domain.Sesion;
import com.mycompany.myapp.domain.Venta;
import com.mycompany.myapp.service.dto.AsientosDTO;
import com.mycompany.myapp.service.dto.EventoDTO;
import com.mycompany.myapp.service.dto.SesionDTO;
import com.mycompany.myapp.service.dto.VentaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Asientos} and its DTO {@link AsientosDTO}.
 */
@Mapper(componentModel = "spring")
public interface AsientosMapper extends EntityMapper<AsientosDTO, Asientos> {
    @Mapping(target = "evento", source = "evento", qualifiedByName = "eventoId")
    @Mapping(target = "venta", source = "venta", qualifiedByName = "ventaId")
    @Mapping(target = "sesion", source = "sesion", qualifiedByName = "sesionId")
    AsientosDTO toDto(Asientos s);

    @Named("eventoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventoDTO toDtoEventoId(Evento evento);

    @Named("ventaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VentaDTO toDtoVentaId(Venta venta);

    @Named("sesionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SesionDTO toDtoSesionId(Sesion sesion);
}
