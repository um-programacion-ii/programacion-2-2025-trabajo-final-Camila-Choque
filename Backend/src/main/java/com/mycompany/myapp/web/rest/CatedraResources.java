package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.service.CatedraServices;
import com.mycompany.myapp.service.dto.*;
import com.mycompany.myapp.service.impl.AsientosDisponiblesService;
import jakarta.annotation.security.PermitAll;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
// Dieccion con la que el frontend se comunica con el back
@RequestMapping("/api/catedra")
@PermitAll
public class CatedraResources {
    private final CatedraServices catedraServices;

    private final AsientosDisponiblesService disponibilidadAsientosService;

    public CatedraResources(CatedraServices catedraServices, AsientosDisponiblesService disponibilidadAsientosService) {
        this.catedraServices = catedraServices;

        this.disponibilidadAsientosService = disponibilidadAsientosService;
    }

    //Si nos queremos registrar hay que hacer un post a /catedra/registrar
    @PostMapping("/registrar")
    public String registrar(@RequestBody String body) {
        return catedraServices.registrar(body);
    }

    @GetMapping("eventos/{id}")
    public EventoExternoDTO conseguirEvento(@PathVariable Long id) {
        return catedraServices.conseguirEventoPorId(id.toString());
    }
    @GetMapping("/eventos")
    public List<EventoExternoDTO> conseguirEventos() {
        return catedraServices.conseguirEventos();
    }

    @GetMapping("/eventos-resumidos")
    public List<EventoResumidoDTO> conseguirEventosResumidos() {
        return catedraServices.conseguirEventosResumidos();
    }

    @PostMapping("/bloqueo-asientos")
    public BloquearAsientosDTO bloquearAsientos (@RequestBody BloquearAsientosDTO body) {
        return catedraServices.bloquearAsientos(body);
    }
    @GetMapping("/asientos/evento/{eventoId}")
    public AsientosRedisDTO getAsientosEvento(@PathVariable Long eventoId) {
        return catedraServices.getAsientos(eventoId);
    }

    @GetMapping("/asientos/evento/{eventoId}/no-disponibles")
    public List<AsientosCompletoDTO> getAsientosNoDisponibles(@PathVariable Long eventoId) {
        return catedraServices.getAsientosNoDisponibles(eventoId);
    }
    @GetMapping("/asientos/evento/{eventoId}/vendidos")
    public List<AsientosRedisDTO> getAsientosVendidos(@PathVariable Long eventoId) {
        return catedraServices.getAsientosVendidos(eventoId);
    }
    @GetMapping("/asientos/evento/{eventoId}/bloqueados")
    public List<AsientosRedisDTO> getAsientosBloqueados(@PathVariable Long eventoId) {
        return catedraServices.getAsientosBloqueados(eventoId);
    }

    @GetMapping("/estadisticas/evento/{eventoId}")
    public Map<String, Object> getEstadisticasEvento(@PathVariable Long eventoId) {
        return catedraServices.getEstadisticasEvento(eventoId);
    }

    @GetMapping("/debug/keys")
    public Set<String> getAllKeys() {
        return catedraServices.getAllKeys();
    }

    @GetMapping("/debug/all")
    public Map<String, String> getAllKeysWithValues() {
        return catedraServices.getAllKeysWithValues();
    }

    @GetMapping("/asientos/evento/{eventoId}/disponibles")
    public MapaAsientosDTO getAsientosDisponibles(
        @PathVariable Long eventoId
    ) {
        return disponibilidadAsientosService.obtenerMapaAsientos(eventoId);
    }
    @PostMapping("/venta")
    public VentaAsientosResponse realizarVenta(@RequestBody VentaAsientosRequest body) {
        return catedraServices.realizarVenta(body);
    }

}
