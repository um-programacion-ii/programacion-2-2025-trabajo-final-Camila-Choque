package com.mycompany.myapp.service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloquearAsientosRequest {
    private Long eventoId;
    private List<AsientosProxyDTO> asientos;


}
