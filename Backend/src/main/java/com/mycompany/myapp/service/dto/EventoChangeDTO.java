package com.mycompany.myapp.service.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mycompany.myapp.enum_back.Modifications;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

// Congifuracion de Kafka realizada con IA
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoChangeDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long eventoId;
    private Modifications tipoCambio;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant timestamp;

    private Map<String, Object> datosModificados;
    private String descripcion;

}
