package com.mycompany.myapp.infrastructure.web.controller;
import com.mycompany.myapp.application.service.ObtenerVentasPorUsuarioUseCase;
import com.mycompany.myapp.infrastructure.web.dto.VentaResponse;
import com.mycompany.myapp.infrastructure.web.mapper.VentaDtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final ObtenerVentasPorUsuarioUseCase obtenerVentasPorUsuarioUseCase;
    private final VentaDtoMapper ventaDtoMapper;

    public VentaController(
        ObtenerVentasPorUsuarioUseCase obtenerVentasPorUsuarioUseCase,
        VentaDtoMapper ventaDtoMapper
    ) {
        this.obtenerVentasPorUsuarioUseCase = obtenerVentasPorUsuarioUseCase;
        this.ventaDtoMapper = ventaDtoMapper;
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<VentaResponse>> obtenerVentasPorUsuario(
        @PathVariable Long userId
    ) {
        List<VentaResponse> response = obtenerVentasPorUsuarioUseCase.obtenerVentasPorUsuario(userId)
            .stream()
            .map(ventaDtoMapper::toResponse)
            .toList();

        return ResponseEntity.ok(response);
    }
}
