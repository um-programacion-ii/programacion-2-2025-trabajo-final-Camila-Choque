package com.example.Proxy.resource;
import com.example.Proxy.dto.AsientoRedis;
import com.example.Proxy.dto.AsientosCompletosDTO;
import com.example.Proxy.dto.AsientosRedisDTO;
import com.example.Proxy.services.RedisServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/proxy/asientos")
@Slf4j
public class RedisAsientosResource {
    @Autowired
    private RedisServices redisServices;

    /**
     * Obtiene todos los asientos de un evento con su estado
     */
    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<AsientosRedisDTO> getAsientosEvento(@PathVariable Long eventoId) {
        log.info("Consultando asientos para evento {}", eventoId);
        AsientosRedisDTO asientos = redisServices.getAsientosEvento(eventoId);
        return ResponseEntity.ok(asientos);
    }

    /**
     * Obtiene lista de identificadores de asientos NO disponibles
     */
    @GetMapping("/evento/{eventoId}/no-disponibles")
    public ResponseEntity<List<AsientosCompletosDTO>> getAsientosNoDisponibles(@PathVariable Long eventoId) {
        log.info("Consultando asientos no disponibles para evento {}", eventoId);
        List<AsientosCompletosDTO> noDisponibles = redisServices.getAsientosNoDisponibles(eventoId);
        return ResponseEntity.ok(noDisponibles);
    }

    /**
     * Obtiene solo asientos vendidos
     */
    @GetMapping("/evento/{eventoId}/vendidos")
    public ResponseEntity<List<AsientoRedis>> getAsientosVendidos(@PathVariable Long eventoId) {
        List<AsientoRedis> vendidos = redisServices.getAsientosVendidos(eventoId);
        return ResponseEntity.ok(vendidos);
    }

    /**
     * Obtiene solo asientos bloqueados válidos
     */
    @GetMapping("/evento/{eventoId}/bloqueados")
    public ResponseEntity<List<AsientoRedis>> getAsientosBloqueados(@PathVariable Long eventoId) {
        List<AsientoRedis> bloqueados = redisServices.getAsientosBloqueados(eventoId);
        return ResponseEntity.ok(bloqueados);
    }

    /**
     * Obtiene estadísticas de un evento
     */
    @GetMapping("/evento/{eventoId}/estadisticas")
    public ResponseEntity<Map<String, Object>> getEstadisticas(@PathVariable Long eventoId) {
        Map<String, Object> stats = redisServices.getEstadisticasEvento(eventoId);
        return ResponseEntity.ok(stats);
    }

    /**
     * Endpoint de debug para ver todas las keys en Redis
     */
    @GetMapping("/debug/keys")
    public ResponseEntity<Set<String>> getAllKeys() {
        Set<String> keys = redisServices.getAllKeys();
        return ResponseEntity.ok(keys);
    }

    /**
     * Endpoint de debug para ver el valor de una key específica
     */
    @GetMapping("/debug/key/{key}")
    public ResponseEntity<String> getKeyValue(@PathVariable String key) {
        String value = redisServices.getValue(key);
        return ResponseEntity.ok(value);
    }

    /**
     * Endpoint de debug mejorado: devuelve TODAS las keys con sus valores
     */
    @GetMapping("/debug/all")
    public ResponseEntity<Map<String, String>> getAllKeysWithValues() {
        log.info("Consultando todas las keys con valores");

        try {
            Map<String, String> result = redisServices.getAllKeysWithValues();
            log.info("Se obtuvieron {} keys", result.size());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error en getAllKeysWithValues: {}", e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}