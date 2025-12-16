package com.example.Proxy.domain;
import com.example.Proxy.enum_proxy.Modifications;
import com.example.Proxy.services.BackendNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

// Congifuracion de Kafka realizada con IA
@Service
@Slf4j
public class EventoKafkaListener {
    @Autowired
    private BackendNotificationService backendNotificationService;


    @KafkaListener(topics = "${kafka.topic.eventos}", groupId = "${kafka.consumer.group-id}")
    public void consumirEventoActualizado(String mensaje) {
        try {
            log.info("Notificación de Kafka recibida: {}", mensaje);

            backendNotificationService.notificarSincronizacionEventos(mensaje);

            log.info("Backend notificado exitosamente sobre cambios en eventos");

        } catch (Exception e) {
            log.error("Error procesando mensaje de Kafka: {}", e.getMessage(), e);
        }
    }
}