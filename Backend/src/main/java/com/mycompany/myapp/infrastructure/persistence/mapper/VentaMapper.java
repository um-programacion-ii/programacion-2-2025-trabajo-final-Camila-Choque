package com.mycompany.myapp.infrastructure.persistence.mapper;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.model.VentaModel;
import com.mycompany.myapp.infrastructure.persistence.entity.Venta;
import org.springframework.stereotype.Component;

@Component
public class VentaMapper {

    // JPA → Dominio
    public VentaModel toDomain(Venta venta) {
        if (venta == null) return null;

        VentaModel model = new VentaModel();
        model.setId(venta.getId());
        model.setPrecioVenta(venta.getPrecioVenta());
        model.setResultado(venta.getResultado());
        model.setDescripcion(venta.getDescripcion());
        model.setCantidadAsientos(venta.getCantidadAsientos());
        model.setEstado(venta.getEstado());
        model.setFechaVenta(venta.getFechaVenta());
        model.setAsientos(venta.getAsientos());
        model.setUserId(venta.getUser().getId());

        return model;
    }

    // Dominio → JPA
    public Venta toEntity(VentaModel model, User user) {
        Venta venta = new Venta();
        venta.setId(model.getId());
        venta.setPrecioVenta(model.getPrecioVenta());
        venta.setResultado(model.getResultado());
        venta.setDescripcion(model.getDescripcion());
        venta.setCantidadAsientos(model.getCantidadAsientos());
        venta.setEstado(model.getEstado());
        venta.setFechaVenta(model.getFechaVenta());
        venta.setAsientos(model.getAsientos());
        venta.setUser(user);

        return venta;
    }
}
