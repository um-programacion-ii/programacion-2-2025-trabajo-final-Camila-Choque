package com.mycompany.myapp.service.impl;
import com.mycompany.myapp.domain.Asientos;
import com.mycompany.myapp.domain.Evento;
import com.mycompany.myapp.repository.AsientosRepository;
import com.mycompany.myapp.repository.EventoRepository;
import com.mycompany.myapp.service.client.ProxyClient;
import com.mycompany.myapp.service.dto.AsientosCompletoDTO;
import com.mycompany.myapp.service.dto.EventoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

        // 🔥 SIEMPRE recalcular asientos
        sincronizarAsientos(evento);

        log.debug("Evento {} sincronizado con asientos", evento.getTitulo());
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


    private void sincronizarAsientos(Evento evento) {

        int filas = evento.getFilaAsientos();
        int columnas = evento.getColumnaAsientos();

        // Asientos ocupados desde Redis
        List<AsientosCompletoDTO> ocupados =
            proxyClient.getAsientosNoDisponibles(evento.getId());

        Map<String, String> estadoPorPosicion = new HashMap<>();
        for (AsientosCompletoDTO dto : ocupados) {
            String key = dto.getFila().toString() + "-" + dto.getColumna().toString();
            estadoPorPosicion.put(key, dto.getEstado());
        }

        // Eliminar asientos viejos del evento
        asientosRepository.deleteByEventoId(evento.getId());

        List<Asientos> nuevos = new ArrayList<>();

        // Generar todos los asientos
        for (int f = 1; f <= filas; f++) {
            for (int c = 1; c <= columnas; c++) {

                String key = f + "-" + c;
                String estado = estadoPorPosicion.getOrDefault(key, "DISPONIBLE");

                Asientos asiento = new Asientos();
                asiento.setFila(f);
                asiento.setColumna(c);
                asiento.setEstado(estado);
                asiento.setEvento(evento);

                nuevos.add(asiento);
            }
        }

        asientosRepository.saveAll(nuevos);

        log.info("Asientos sincronizados para evento {} (total: {})",
            evento.getId(), nuevos.size());
    }


}
