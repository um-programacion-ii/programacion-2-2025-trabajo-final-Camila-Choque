package com.mycompany.myapp.service.dto;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
@Data
public class EventoResumidoDTO implements Serializable {
    private Long id;
    private String titulo;
    private String resumen;
    private String descripcion;
    private LocalDate fecha;
    private Double precioEntrada;
    private String eventoTipoNombre;
    private String eventoTipoDescripcion;
}
