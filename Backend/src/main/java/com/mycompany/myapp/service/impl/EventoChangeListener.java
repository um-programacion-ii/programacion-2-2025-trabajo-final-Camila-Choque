package com.mycompany.myapp.service.impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.service.dto.EventoChangeDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
// Congifuracion de Kafka realizada con IA
@Component
public class EventoChangeListener implements MessageListener {

    @Autowired
    private EventoSyncService eventoSyncService;

    @Autowired
    private ObjectMapper objectMapper;

    private final Logger log = LoggerFactory.getLogger(EventoChangeListener.class);

    /**
     * Este método se ejecuta automáticamente cuando llega un mensaje de Redis
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // Obtener el contenido del mensaje
            String messageBody = new String(message.getBody());
            String channel = new String(message.getChannel());

            log.info(" [REDIS PUB/SUB] Mensaje recibido del canal '{}': {}", channel, messageBody);

            // Deserializar el JSON a objeto
            log.info("JSON recibido: {}", messageBody);
            EventoChangeDTO cambio = objectMapper.readValue(messageBody, EventoChangeDTO.class);

            log.info("🔍 [REDIS PUB/SUB] Cambio parseado: eventoId={}, tipo={}",
                cambio.getEventoId(), cambio.getTipoCambio());

            // Procesar el cambio
            procesarCambioEvento(cambio);

            log.info("✅ [REDIS PUB/SUB] Mensaje procesado exitosamente");

        } catch (Exception e) {
            log.error("❌ [REDIS PUB/SUB] Error procesando mensaje: {}", e.getMessage(), e);
            // Aquí podrías implementar una estrategia de reintento o almacenar en cola de errores
        }
    }

    /**
     * Procesa el cambio según el tipo
     */
    private void procesarCambioEvento(EventoChangeDTO cambio) {
        log.info(" Procesando cambio de tipo '{}' para evento ID: {}",
            cambio.getTipoCambio(), cambio.getEventoId());

        try {
            switch (cambio.getTipoCambio()) {
                case NUEVO_EVENTO:
                    log.info(" Sincronizando nuevo evento...");
                    eventoSyncService.sincronizarNuevoEvento(cambio.getEventoId());
                    break;

                case EVENTO_MODIFICADO:
                    log.info(" Actualizando evento modificado...");
                    eventoSyncService.actualizarEvento(cambio.getEventoId(), cambio.getDatosModificados());
                    break;

                case EVENTO_CANCELADO:
                    log.info(" Cancelando evento...");
                    eventoSyncService.cancelarEvento(cambio.getEventoId());
                    break;

                case EVENTO_EXPIRADO:
                    log.info(" Marcando evento como expirado...");
                    eventoSyncService.marcarEventoExpirado(cambio.getEventoId());
                    break;

                case ASIENTOS_ACTUALIZADOS:
                    log.info(" Actualizando información de asientos...");
                    eventoSyncService.actualizarAsientos(cambio.getEventoId());
                    break;

                default:
                    log.warn("Tipo de cambio desconocido: {}", cambio.getTipoCambio());
            }
        } catch (Exception e) {
            log.error("❌ Error en procesarCambioEvento: {}", e.getMessage(), e);
            throw new RuntimeException("Error procesando cambio de evento", e);
        }
    }
}
