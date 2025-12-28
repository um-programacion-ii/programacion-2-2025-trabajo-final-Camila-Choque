package com.mycompany.myapp.infrastructure.web.mapper;
import com.mycompany.myapp.domain.model.VentaModel;
import com.mycompany.myapp.infrastructure.web.dto.AsientoResponse;
import com.mycompany.myapp.infrastructure.web.dto.VentaResponse;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class VentaDtoMapper {

    public VentaResponse toResponse(VentaModel model) {
        if (model == null) {
            return null;
        }

        List<AsientoResponse> asientos = null;

        return new VentaResponse(
            model.getId(),
            model.getVentaIdCatedra(),
            model.getFechaVenta(),
            model.getPrecioVenta(),
            model.getResultado(),
            model.getDescripcion(),
            model.getCantidadAsientos(),
            model.getEstado(),
            asientos
        );
    }
}

