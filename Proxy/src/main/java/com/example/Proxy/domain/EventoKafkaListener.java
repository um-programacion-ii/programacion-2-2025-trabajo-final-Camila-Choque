package com.example.Proxy.domain;
import com.example.Proxy.dto.EventoChangeDTO;
import com.example.Proxy.enum_proxy.Modifications;
import com.example.Proxy.services.BackendNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

// Congifuracion de Kafka realizada con IA
@Service
@Slf4j
public class EventoKafkaListener {

    @Autowired
    private BackendNotificationService backendNotificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka.topic.eventos}", groupId = "${kafka.consumer.group-id}")
    public void consumirEventoActualizado(String mensaje) {
        try {
            log.info("Mensaje recibido de Kafka: {}", mensaje);

            // Parsear el mensaje JSON
            EventoChangeDTO cambio = objectMapper.readValue(mensaje, EventoChangeDTO.class);

            // Procesar según el tipo de cambio
            procesarCambioEvento(cambio);

            // Notificar al backend
            backendNotificationService.notificarCambioEvento(cambio);

        } catch (Exception e) {
            log.error("Error procesando mensaje de Kafka: {}", e.getMessage(), e);
        }
    }

    private void procesarCambioEvento(EventoChangeDTO cambio) {
        switch (cambio.getTipoCambio()) {
            case Modifications.NUEVO_EVENTO:
                log.info("Nuevo evento creado: {}", cambio.getEventoId());
                break;
            case Modifications.EVENTO_MODIFICADO:
                log.info("Evento modificado: {}", cambio.getEventoId());
                break;
            case Modifications.EVENTO_CANCELADO:
                log.info("Evento cancelado: {}", cambio.getEventoId());
                break;
            case Modifications.EVENTO_EXPIRADO:
                log.info("Evento expirado: {}", cambio.getEventoId());
                break;
            default:
                log.warn("Tipo de cambio desconocido: {}", cambio.getTipoCambio());
        }
    }
}