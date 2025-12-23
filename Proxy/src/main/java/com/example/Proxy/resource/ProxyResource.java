package com.example.Proxy.resource;
import com.example.Proxy.dto.*;
import com.example.Proxy.services.ProxyServices;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/proxy")
public class ProxyResource {
    private final ProxyServices proxyService;

    public ProxyResource(ProxyServices proxyService) {
        this.proxyService = proxyService;
    }

    @PostMapping("/registrar")
    public RegistrarUsuarioResponse registrarUsuario(@RequestBody RegistrarUsuarioDto dto) {
        return proxyService.registarUsuario(dto);
    }

    @PostMapping("/login")
    public String Login(@RequestBody LoginDto dto) {
        return proxyService.login(dto);
    }
    @GetMapping("/eventos-resumidos")
    public List<EventoResumidoDto> conseguirEventosResumidos() {
        return proxyService.conseguirEventosResumidos();
    }

    @GetMapping("/eventos")
    public List<EventoDto> conseguirEventos() {
        return proxyService.conseguirEventos();
    }

    @GetMapping("/eventos/{id}")
    public EventoDto conseguirEventosPorId(@PathVariable Long id) {
        return proxyService.conseguirEventosPorId(id);
    }

    @PostMapping("/bloquear-asientos")
    public BloquearAsientosDTO bloquearAsientos(@RequestBody BloquearAsientosRequest dto) {
        return proxyService.bloquearAsientos(dto);
    }
    @PostMapping("/venta")
    public VentaAsientosResponse realizarVenta(@RequestBody VentaAsientosRequest request) {
        return proxyService.realizarVenta(request);
    }
}