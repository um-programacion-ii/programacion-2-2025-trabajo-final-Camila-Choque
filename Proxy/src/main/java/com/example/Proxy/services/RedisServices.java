package com.example.Proxy.services;
import com.example.Proxy.dto.AsientoRedis;
import com.example.Proxy.dto.AsientosDTO;
import com.example.Proxy.dto.AsientosRedisDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RedisServices {

    @Autowired
    @Qualifier("redisTemplateString")
    private RedisTemplate<String, String> redisTemplate;

    private final ObjectMapper objectMapper;

    public RedisServices() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.findAndRegisterModules();
    }

    /**
     * Obtiene los asientos vendidos y bloqueados de un evento desde Redis
     * @param eventoId ID del evento
     * @return DTO con todos los asientos y su estado
     */
    public AsientosRedisDTO getAsientosEvento(Long eventoId) {
        try {

            String key = "evento_" + eventoId;

            String jsonData = redisTemplate.opsForValue().get(key);
            log.info("Key buscada: {}", key);
            log.info("JSON obtenido: {}", jsonData);

            if (jsonData != null && !jsonData.isEmpty()) {
                return objectMapper.readValue(jsonData, AsientosRedisDTO.class);
            }


            AsientosRedisDTO dto = new AsientosRedisDTO();
            dto.setEventoId(eventoId);
            dto.setAsientos(new ArrayList<>());
            return dto;

        } catch (Exception e) {
            log.error("Error al consultar Redis para evento {}: {}", eventoId, e.getMessage(), e);
            AsientosRedisDTO dto = new AsientosRedisDTO();
            dto.setEventoId(eventoId);
            dto.setAsientos(new ArrayList<>());
            return dto;
        }
    }

    /**
     * Obtiene los asientos NO disponibles (vendidos + bloqueados válidos)
     * @param eventoId ID del evento
     * @return Lista de identificadores de asientos no disponibles
     */
    public List<String> getAsientosNoDisponibles(Long eventoId) {
        AsientosRedisDTO datos = getAsientosEvento(eventoId);

        return datos.getAsientos().stream()
                .filter(asiento -> asiento.esVendido() || asiento.esBloqueadoValido())
                .map(AsientoRedis::getIdentificador)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene solo los asientos vendidos
     */
    public List<AsientoRedis> getAsientosVendidos(Long eventoId) {
        AsientosRedisDTO datos = getAsientosEvento(eventoId);

        return datos.getAsientos().stream()
                .filter(AsientoRedis::esVendido)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene solo los asientos bloqueados válidos
     */
    public List<AsientoRedis> getAsientosBloqueados(Long eventoId) {
        AsientosRedisDTO datos = getAsientosEvento(eventoId);

        return datos.getAsientos().stream()
                .filter(AsientoRedis::esBloqueadoValido)
                .collect(Collectors.toList());
    }

    /**
     * Verifica si un asiento específico está disponible
     */
    public boolean isAsientoDisponible(Long eventoId, int fila, int columna) {
        AsientosRedisDTO datos = getAsientosEvento(eventoId);

        return datos.getAsientos().stream()
                .filter(a -> a.getFila().equals(fila) && a.getColumna().equals(columna))
                .noneMatch(a -> a.esVendido() || a.esBloqueadoValido());
    }

    /**
     * Obtiene estadísticas de un evento
     */
    public Map<String, Object> getEstadisticasEvento(Long eventoId) {
        AsientosRedisDTO datos = getAsientosEvento(eventoId);

        long vendidos = datos.getAsientos().stream()
                .filter(AsientoRedis::esVendido)
                .count();

        long bloqueados = datos.getAsientos().stream()
                .filter(AsientoRedis::esBloqueadoValido)
                .count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("eventoId", eventoId);
        stats.put("totalAsientos", datos.getAsientos().size());
        stats.put("vendidos", vendidos);
        stats.put("bloqueados", bloqueados);
        stats.put("noDisponibles", vendidos + bloqueados);

        return stats;
    }

    /**
     * Explora las keys en Redis (útil para debug)
     * @return Set de todas las keys
     */
    public Set<String> getAllKeys() {
        return redisTemplate.keys("evento_*");
    }

    /**
     * Verifica la conexión con Redis
     */
    public boolean isRedisConnected() {
        try {
            redisTemplate.getConnectionFactory().getConnection().ping();
            return true;
        } catch (Exception e) {
            log.error("Redis no está conectado: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene todas las keys con sus valores de forma más segura
     */
    public Map<String, String> getAllKeysWithValues() {
        Map<String, String> result = new HashMap<>();

        try {
            // Primero verificar conexión
            if (!isRedisConnected()) {
                log.error("No hay conexión con Redis");
                return result;
            }

            Set<String> keys = getAllKeys();
            log.info("Encontradas {} keys en Redis", keys.size());

            for (String key : keys) {
                try {
                    String value = getValue(key);
                    if (value != null) {
                        result.put(key, value);
                    }
                } catch (Exception e) {
                    log.warn("Error obteniendo valor de key '{}': {}", key, e.getMessage());
                    result.put(key, "ERROR: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            log.error("Error obteniendo keys con valores: {}", e.getMessage(), e);
        }

        return result;}

    /**
     * Obtiene el valor de una key específica (útil para debug)
     * @param key La key a consultar
     * @return El valor como String
     */
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
