package com.mycompany.myapp.service.impl;
import com.mycompany.myapp.domain.Evento;
import com.mycompany.myapp.repository.EventoRepository;
import com.mycompany.myapp.service.CatedraServices;
import com.mycompany.myapp.service.dto.EventoDTO;
import com.mycompany.myapp.service.mapper.EventoMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
// Congifuracion de Kafka realizada con IA
@Service
public class EventoSyncService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private CatedraServices catedraApiService;

    @Autowired
    private EventoMapper eventoMapper;

    private final Logger log = LoggerFactory.getLogger(EventoChangeListener.class);


    /**
     * Sincroniza un nuevo evento desde el servicio de la cátedra
     */
    @Transactional
    public void sincronizarNuevoEvento(Long eventoId) {
        try {
            log.info(" Iniciando sincronización de nuevo evento ID: {}", eventoId);

            // Verificar si ya existe (por si acaso)
            if (eventoRepository.existsById(eventoId)) {
                log.warn("El evento {} ya existe en la base de datos. Actualizando...", eventoId);
                actualizarEvento(eventoId, null);
                return;
            }

            // Obtener datos completos del evento desde la cátedra
            EventoDTO evento = catedraApiService.conseguirEventoPorId(eventoId.toString());

            if (evento == null) {
                log.error(" No se pudo obtener el evento {} desde la cátedra", eventoId);
                return;
            }

            // Establecer estado inicial
            evento.setEstado("ACTIVO");
            Evento new_evento = eventoMapper.toEntity(evento);
            Evento eventoGuardado = eventoRepository.save(new_evento);

            log.info(" Evento {} sincronizado y guardado correctamente con ID: {}",
                eventoId, eventoGuardado.getId());

        } catch (Exception e) {
            log.error(" Error sincronizando nuevo evento {}: {}", eventoId, e.getMessage(), e);
            throw new RuntimeException("Error en sincronizarNuevoEvento", e);
        }
    }

    /**
     * Actualiza un evento existente
     */
    @Transactional
    public void actualizarEvento(Long eventoId, Map<String, Object> datosModificados) {
        try {
            log.info(" Iniciando actualización de evento ID: {}", eventoId);

            // Buscar evento local
            Evento evento = eventoRepository.findById(eventoId)
                .orElse(null);


            if (evento == null) {
                log.warn(" El evento {} no existe localmente. Sincronizando...", eventoId);
                sincronizarNuevoEvento(eventoId);
                return;
            }

            // Opción 1: Si vienen los datos modificados, actualizar solo esos campos
            if (datosModificados != null && !datosModificados.isEmpty()) {
                log.info(" Actualizando campos específicos: {}", datosModificados.keySet());
                actualizarCamposEspecificos(evento, datosModificados);
            } else {
                // Opción 2: Resincronizar todo el evento desde la cátedra
                log.info(" Resincronizando evento completo desde la cátedra...");
                EventoDTO eventoActualizado = catedraApiService.conseguirEventoPorId(eventoId.toString());

                EventoDTO new_evento = eventoMapper.toDto(evento);
                if (eventoActualizado != null) {
                    copiarDatosEvento(eventoActualizado, new_evento);
                }
            }

            eventoRepository.save(evento);

            log.info(" Evento {} actualizado correctamente", eventoId);

        } catch (Exception e) {
            log.error(" Error actualizando evento {}: {}", eventoId, e.getMessage(), e);
            throw new RuntimeException("Error en actualizarEvento", e);
        }
    }

    /**
     * Marca un evento como cancelado
     */
    @Transactional
    public void cancelarEvento(Long eventoId) {
        try {
            log.info(" Cancelando evento ID: {}", eventoId);

            Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado: " + eventoId));

            evento.setEstado("CANCELADO");
            eventoRepository.save(evento);

            log.info(" Evento {} marcado como CANCELADO", eventoId);

        } catch (Exception e) {
            log.error(" Error cancelando evento {}: {}", eventoId, e.getMessage(), e);
            throw new RuntimeException("Error en cancelarEvento", e);
        }
    }

    /**
     * Marca un evento como expirado
     */
    @Transactional
    public void marcarEventoExpirado(Long eventoId) {
        try {
            log.info(" Marcando evento como expirado ID: {}", eventoId);

            Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado: " + eventoId));

            evento.setEstado("EXPIRADO");
            eventoRepository.save(evento);

            log.info(" Evento {} marcado como EXPIRADO", eventoId);

        } catch (Exception e) {
            log.error(" Error marcando evento expirado {}: {}", eventoId, e.getMessage(), e);
            throw new RuntimeException("Error en marcarEventoExpirado", e);
        }
    }

    /**
     * Actualiza la información de asientos
     * Nota: Los asientos se consultan desde Redis de la cátedra a través del proxy
     */
    public void actualizarAsientos(Long eventoId) {
        try {
            log.info(" Notificación de actualización de asientos para evento ID: {}", eventoId);

            // Los asientos se obtienen bajo demanda desde Redis de la cátedra vía proxy
            // Aquí solo registramos que hubo un cambio
            // Podrías invalidar un cache si lo tuvieras

            log.info(" Notificación de cambio de asientos registrada para evento {}", eventoId);

        } catch (Exception e) {
            log.error(" Error procesando actualización de asientos {}: {}", eventoId, e.getMessage(), e);
        }
    }

    /**
     * Actualiza campos específicos del evento
     */
    private void actualizarCamposEspecificos(Evento evento, Map<String, Object> datos) {
        datos.forEach((campo, valor) -> {
            log.debug("Actualizando campo '{}' con valor: {}", campo, valor);

            switch (campo) {
                case "titulo":
                    evento.setTitulo((String) valor);
                    break;
                case "descripcion":
                    evento.setDescripcion((String) valor);
                    break;
                case "resumen":
                    evento.setResumen((String) valor);
                    break;
                case "direccion":
                    evento.setDireccion((String) valor);
                    break;
                case "precioEntrada":
                    evento.setPrecioEntrada(((Number) valor).doubleValue());
                    break;
                // Agregar más campos según tu entidad Evento
                default:
                    log.warn("⚠️ Campo desconocido: {}", campo);
            }
        });
    }

    /**
     * Copia datos de un evento a otro
     */
    private void copiarDatosEvento(EventoDTO origen, EventoDTO destino) {
        destino.setTitulo(origen.getTitulo());
        destino.setResumen(origen.getResumen());
        destino.setDescripcion(origen.getDescripcion());
        destino.setFecha(origen.getFecha());
        destino.setDireccion(origen.getDireccion());
        destino.setImagen(origen.getImagen());
        destino.setFilaAsientos(origen.getFilaAsientos());
        destino.setColumnaAsientos(origen.getColumnaAsientos());
        destino.setPrecioEntrada(origen.getPrecioEntrada());
        // No copiar el estado, mantener el actual
        // Copiar otros campos según tu entidad
    }
}

