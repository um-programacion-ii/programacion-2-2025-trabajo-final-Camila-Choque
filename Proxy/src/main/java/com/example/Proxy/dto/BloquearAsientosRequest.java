package com.example.Proxy.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloquearAsientosRequest {
    private Long eventoId;
    private List<AsientosDTO> asientos;
}
