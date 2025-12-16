package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.security.JwtUtils;
import com.mycompany.myapp.service.dto.NotificacionDTO;
import com.mycompany.myapp.service.impl.EventoSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/internal/notificacion")
@Slf4j
public class NotificationResource {
    @Autowired
    private JwtUtils jwtTokenUtil;

    @Autowired
    private EventoSyncService eventoSyncService;

    /**
     * Endpoint que recibe notificaciones del proxy sobre cambios en eventos
     * Requiere autenticación JWT en el header
     */
    @PostMapping("/eventos/cambios")
    public ResponseEntity<Map<String, Object>> recibirNotificacionEventos(
        @RequestHeader(value = "Authorization", required = false) String authHeader,
        @RequestBody NotificacionDTO notificacion) {

        log.info("Recibida notificación de cambios en eventos: {}", notificacion.getMensaje());

        // Validar token JWT
        String token = extraerToken(authHeader);
        if (token == null || !jwtTokenUtil.validateToken(token)) {
            log.error("Token inválido o ausente");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(crearRespuestaError("Token inválido o ausente"));
        }

        // Verificar que el token no haya expirado
        if (jwtTokenUtil.isTokenExpired(token)) {
            log.error("Token expirado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(crearRespuestaError("Token expirado"));
        }

        // Extraer información del token (opcional, para logging)
        String emisor = jwtTokenUtil.getSubjectFromToken(token);
        log.info("Notificación recibida de: {}", emisor);

        try {
            // Procesar la notificación
            if ("SINCRONIZAR_EVENTOS".equals(notificacion.getTipo())) {
                log.info("Iniciando sincronización de eventos desde la cátedra");
                eventoSyncService.sincronizarEventos();

                return ResponseEntity.ok(crearRespuestaExito(
                    "Sincronización de eventos iniciada correctamente"
                ));
            } else {
                log.warn("Tipo de notificación desconocido: {}", notificacion.getTipo());
                return ResponseEntity.badRequest()
                    .body(crearRespuestaError("Tipo de notificación no soportado"));
            }

        } catch (Exception e) {
            log.error("Error procesando notificación: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(crearRespuestaError("Error interno procesando notificación"));
        }
    }

    /**
     * Extrae el token JWT del header Authorization
     */
    private String extraerToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    /**
     * Crea una respuesta de éxito
     */
    private Map<String, Object> crearRespuestaExito(String mensaje) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("mensaje", mensaje);
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }

    /**
     * Crea una respuesta de error
     */
    private Map<String, Object> crearRespuestaError(String mensaje) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", mensaje);
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }
}
