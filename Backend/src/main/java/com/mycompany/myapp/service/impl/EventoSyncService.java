package com.mycompany.myapp.service.impl;
import com.mycompany.myapp.domain.Asientos;
import com.mycompany.myapp.domain.Evento;
import com.mycompany.myapp.repository.AsientosRepository;
import com.mycompany.myapp.repository.EventoRepository;
import com.mycompany.myapp.service.CatedraServices;
import com.mycompany.myapp.service.client.ProxyClient;
import com.mycompany.myapp.service.dto.EventoDTO;
import com.mycompany.myapp.service.mapper.EventoMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

// Congifuracion de Kafka realizada con IA

@Service
@Slf4j
public class EventoSyncService {
    @Autowired
    private ProxyClient proxyClient;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private AsientosRepository asientosRepository;

    /**
     * Sincroniza todos los eventos desde la cátedra
     * Se ejecuta de forma asíncrona para no bloquear la respuesta
     */
    @Async
    @Transactional
    public void sincronizarEventos() {
        log.info("Iniciando sincronización de eventos...");

        try {
            // Obtener todos los eventos desde la cátedra
            List<EventoDTO> eventosActualizados = proxyClient.conseguirEventos();

            log.info("Se obtuvieron {} eventos de la cátedra", eventosActualizados.size());

            // Actualizar o crear eventos en la BD local
            for (EventoDTO eventoDTO : eventosActualizados) {
                sincronizarEvento(eventoDTO);
            }

            // Marcar como eliminados los eventos que ya no existen en la cátedra
            marcarEventosEliminados(eventosActualizados);

            log.info("Sincronización de eventos completada exitosamente");

        } catch (Exception e) {
            log.error("Error sincronizando eventos: {}", e.getMessage(), e);
        }
    }

    /**
     * Sincroniza un evento individual
     */
    private void sincronizarEvento(EventoDTO eventoDTO) {
        try {

            boolean eventoNuevo = !eventoRepository.existsById(eventoDTO.getId());

            Evento evento = eventoRepository.findById(eventoDTO.getId())
                .orElse(new Evento());

            evento.setId(eventoDTO.getId());
            evento.setTitulo(eventoDTO.getTitulo());
            evento.setDescripcion(eventoDTO.getDescripcion());
            evento.setFecha(eventoDTO.getFecha());
            evento.setFilaAsientos(eventoDTO.getFilaAsientos());
            evento.setColumnaAsientos(eventoDTO.getColumnAsientos());
            evento.setPrecioEntrada(eventoDTO.getPrecioEntrada());
            evento.setEstado("ACTIVO");

            eventoRepository.save(evento);

            // 👇 SOLO si el evento es nuevo
            if (eventoNuevo) {
                generarAsientos(evento);
                log.info("Asientos generados para evento {}", evento.getId());
            }

            log.debug("Evento {} sincronizado", evento.getTitulo());

        } catch (Exception e) {
            log.error("Error sincronizando evento {}: {}", eventoDTO.getId(), e.getMessage());
        }
    }


    /**
     * Marca como eliminados los eventos que ya no existen en la cátedra
     */
    private void marcarEventosEliminados(List<EventoDTO> eventosActualizados) {
        try {
            List<Long> idsActualizados = eventosActualizados.stream()
                .map(EventoDTO::getId)
                .toList();

            List<Evento> eventosLocales = eventoRepository.findByEstado("ACTIVO");


            for (Evento evento : eventosLocales) {
                if (!idsActualizados.contains(evento.getId())) {
                    evento.setEstado("INACTIVO");
                    eventoRepository.save(evento);
                    log.info("Evento {} marcado como inactivo", evento.getTitulo());
                }
            }

        } catch (Exception e) {
            log.error("Error marcando eventos eliminados: {}", e.getMessage());
        }
    }
    private void generarAsientos(Evento evento) {

        int filas = evento.getFilaAsientos();
        int columnas = evento.getColumnaAsientos();

        List<Asientos> asientos = new ArrayList<>();

        for (int f = 1; f <= filas; f++) {
            for (int c = 1; c <= columnas; c++) {

                Asientos asiento = new Asientos();
                asiento.setFila(f);
                asiento.setColumna(c);
                asiento.setEstado("DISPONIBLE");
                asiento.setEvento(evento);

                asientos.add(asiento);
            }
        }

        asientosRepository.saveAll(asientos);
    }

}
