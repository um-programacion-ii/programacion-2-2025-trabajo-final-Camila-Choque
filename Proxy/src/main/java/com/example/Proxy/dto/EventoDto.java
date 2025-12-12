package com.example.Proxy.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoDto {

    private String titulo;
    private String resumen;
    private String descripcion;
    private LocalDateTime fecha;
    private String direccion;
    private String imagen;
    private Integer filaAsientos;
    private Integer columnAsientos;
    private Double precioEntrada;
    private EventoTipoDTO eventoTipo;
    private List<IntegrantedDto> integrantes;
    private Long id;



}