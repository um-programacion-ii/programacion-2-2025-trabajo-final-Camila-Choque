package com.mycompany.myapp.service.impl;
import com.mycompany.myapp.domain.Asientos;
import com.mycompany.myapp.domain.Evento;
import com.mycompany.myapp.repository.AsientosRepository;
import com.mycompany.myapp.repository.EventoRepository;
import com.mycompany.myapp.service.client.ProxyClient;
import com.mycompany.myapp.service.dto.AsientosCompletoDTO;
import com.mycompany.myapp.service.dto.EventoExternoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class EventoSyncService {
    @Autowired
    private ProxyClient proxyClient;
    @Autowired
    private EventoRepository eventoRepository;

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
            List<EventoExternoDTO> eventosActualizados = proxyClient.conseguirEventos();

            log.info("Se obtuvieron {} eventos de la cátedra", eventosActualizados.size());

            // Actualizar o crear eventos en la BD local
            for (EventoExternoDTO eventoDTO : eventosActualizados) {
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
    private void sincronizarEvento(EventoExternoDTO eventoDTO) {

        Evento evento = eventoRepository.findById(eventoDTO.getId())
            .orElse(new Evento());

        evento.setId(eventoDTO.getId());
        evento.setTitulo(eventoDTO.getTitulo());
        evento.setResumen(eventoDTO.getResumen());
        evento.setDescripcion(eventoDTO.getDescripcion());
        evento.setFecha(eventoDTO.getFecha());
        evento.setFilaAsientos(eventoDTO.getFilaAsientos());
        evento.setColumnaAsientos(eventoDTO.getColumnAsientos());
        evento.setPrecioEntrada(eventoDTO.getPrecioEntrada());
        evento.setEventoTipoNombre(eventoDTO.getEventoTipo().getNombre());
        evento.setEventoTipoDescripcion(eventoDTO.getEventoTipo().getDescripcion());
        evento.setEstado("ACTIVO");
        evento.setUltimaActualizacion(LocalDate.now());

        eventoRepository.save(evento);

        log.debug("Evento {} sincronizado con asientos", evento.getTitulo());
    }

    /**
     * Marca como eliminados los eventos que ya no existen en la cátedra
     */
    private void marcarEventosEliminados(List<EventoExternoDTO> eventosActualizados) {
        try {
            List<Long> idsActualizados = eventosActualizados.stream()
                .map(EventoExternoDTO::getId)
                .toList();

            List<Evento> eventosLocales = eventoRepository.findByEstado("Activo");

            for (Evento evento : eventosLocales) {
                if (!idsActualizados.contains(evento.getId())) {
                    evento.setEstado("Inactivo");
                    eventoRepository.save(evento);
                    log.info("Evento {} marcado como inactivo", evento.getTitulo());
                }
            }

        } catch (Exception e) {
            log.error("Error marcando eventos eliminados: {}", e.getMessage());
        }
    }


}
