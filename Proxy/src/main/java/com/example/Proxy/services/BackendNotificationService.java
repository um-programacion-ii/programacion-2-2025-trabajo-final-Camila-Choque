package com.example.Proxy.services;
import com.example.Proxy.domain.BackendClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

// Congifuracion de Kafka realizada con IA
@Service
@Slf4j
public class BackendNotificationService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private BackendClient backendClient;

    /**
     * Notifica al backend que debe sincronizar los eventos
     * @param mensajeKafka El mensaje recibido de Kafka
     */
    public void notificarSincronizacionEventos(String mensajeKafka) {
        log.info("Notificando al backend sobre cambios en eventos");

        try {
            Map<String, Object> body = prepararNotificacion(mensajeKafka);

            backendClient.notificarCambioEvento(body)
                    .doOnSuccess(res -> {
                        log.info("Backend notificado exitosamente");
                        guardarUltimaNotificacion(mensajeKafka);
                    })
                    .doOnError(err -> {
                        log.error("Error notificando al backend: {}", err.getMessage());
                        manejarErrorNotificacion(mensajeKafka, err);
                    })
                    .subscribe();

        } catch (Exception e) {
            log.error("Error preparando notificación: {}", e.getMessage());
            manejarErrorNotificacion(mensajeKafka, e);
        }
    }

    /**
     * Prepara el body de la notificación al backend
     */
    private Map<String, Object> prepararNotificacion(String mensajeKafka) {
        Map<String, Object> body = new HashMap<>();
        body.put("tipo", "SINCRONIZAR_EVENTOS");
        body.put("mensaje", mensajeKafka);
        body.put("timestamp", LocalDateTime.now());
        return body;
    }

    /**
     * Guarda la última notificación en Redis para auditoría
     */
    private void guardarUltimaNotificacion(String mensaje) {
        try {
            String key = "eventos:ultima-notificacion";

            Map<String, Object> notificacion = new HashMap<>();
            notificacion.put("mensaje", mensaje);
            notificacion.put("timestamp", LocalDateTime.now().toString());

            redisTemplate.opsForValue().set(key, notificacion);
            redisTemplate.expire(key, 1, TimeUnit.HOURS);

            log.debug("Última notificación guardada en Redis");
        } catch (Exception e) {
            log.warn("No se pudo guardar última notificación en Redis: {}", e.getMessage());
        }
    }

    /**
     * Maneja errores cuando no se puede notificar al backend
     */
    private void manejarErrorNotificacion(String mensaje, Throwable e) {
        try {
            String retryKey = "eventos:retry-queue";

            Map<String, Object> retryItem = new HashMap<>();
            retryItem.put("mensaje", mensaje);
            retryItem.put("timestamp", LocalDateTime.now().toString());
            retryItem.put("error", e.getMessage());

            redisTemplate.opsForList().rightPush(retryKey, retryItem);
            log.info("Notificación agregada a cola de reintentos");
        } catch (Exception ex) {
            log.error("Error crítico: no se pudo notificar ni guardar en cola de reintentos: {}", ex.getMessage());
        }
    }

}