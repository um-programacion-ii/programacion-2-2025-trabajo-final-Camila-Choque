package com.example.Proxy.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public  class AsientoRedis {

    @JsonProperty("fila")
    private Integer fila;

    @JsonProperty("columna")
    private Integer columna;

    @JsonProperty("estado")
    private String estado; // "Vendido" o "Bloqueado"

    @JsonProperty("expira")
    private ZonedDateTime expira; // Solo presente en bloqueados

    @JsonProperty("expiraEpoch")
    private Long expiraEpoch;

    @JsonProperty("seatId")
    private String seatId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("holder")
    private String holder; // Quien tiene el bloqueo

    @JsonProperty("updatedAt")
    private ZonedDateTime updatedAt;

    @JsonProperty("updatedAtEpoch")
    private Long updatedAtEpoch;

    @JsonProperty("comprador")
    private Comprador comprador; // Solo presente en vendidos

    @JsonProperty("fechaVenta")
    private ZonedDateTime fechaVenta;

    /**
     * Verifica si el asiento está bloqueado y el bloqueo aún es válido
     */
    public boolean esBloqueadoValido() {
        if (!"Bloqueado".equalsIgnoreCase(estado) && !"BLOQUEADO".equalsIgnoreCase(status)) {
            return false;
        }

        if (expira == null) {
            return false;
        }

        return expira.isAfter(ZonedDateTime.now());
    }

    /**
     * Verifica si el asiento está vendido
     */
    public boolean esVendido() {
        return "Vendido".equalsIgnoreCase(estado) || "VENDIDO".equalsIgnoreCase(status);
    }

    /**
     * Obtiene un identificador único del asiento
     */
    public String getIdentificador() {
        if (seatId != null) {
            return seatId;
        }
        return "r" + fila + "c" + columna;
    }
}