package com.mycompany.myapp.service.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDTO {

    @JsonProperty("tipo")
    private String tipo; // "SINCRONIZAR_EVENTOS"

    @JsonProperty("mensaje")
    private String mensaje;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
}
