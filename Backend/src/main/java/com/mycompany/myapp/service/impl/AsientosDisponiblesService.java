package com.mycompany.myapp.service.impl;
import com.mycompany.myapp.domain.Asientos;
import com.mycompany.myapp.domain.Evento;
import com.mycompany.myapp.repository.AsientosRepository;
import com.mycompany.myapp.repository.EventoRepository;
import com.mycompany.myapp.service.CatedraServices;
import com.mycompany.myapp.service.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AsientosDisponiblesService {

    private final CatedraServices catedraServices;
    private final EventoRepository eventoRepository;

    public AsientosDisponiblesService(
        AsientosRepository asientosRepository,
        CatedraServices catedraServices, EventoRepository eventoRepository) {

        this.catedraServices = catedraServices;
        this.eventoRepository = eventoRepository;
    }

    public MapaAsientosDTO obtenerMapaAsientos(Long eventoId) {
        /*
        Evento evento = eventoRepository.findById(eventoId)
            .orElseThrow(() -> new IllegalArgumentException("Evento inexistente"));
        */
        EventoExternoDTO evento = catedraServices.conseguirEventoPorId(eventoId.toString());
        AsientosRedisDTO redisDTO = catedraServices.getAsientos(eventoId);

        Map<String, String> estadoPorPosicion = new HashMap<>();

        for (AsientosRedis a : redisDTO.getAsientos()) {
            String key = a.getFila() + "-" + a.getColumna();

            if ("Vendido".equalsIgnoreCase(a.getEstado())) {
                estadoPorPosicion.put(key, "VENDIDO");
            }
            else if (a.esBloqueadoValido()) {
                estadoPorPosicion.put(key, "BLOQUEADO");
            }
        }

        List<FilaAsientosDTO> filas = new ArrayList<>();

        for (int f = 1; f <= evento.getFilaAsientos(); f++) {
            List<FilaAsientosDTO.AsientoEstadoDTO> columnas = new ArrayList<>();

            for (int c = 1; c <= evento.getColumnAsientos(); c++) {
                String key = f + "-" + c;
                String estado = estadoPorPosicion.getOrDefault(key, "DISPONIBLE");

                columnas.add(new FilaAsientosDTO.AsientoEstadoDTO(c, estado));
            }

            filas.add(new FilaAsientosDTO(f, columnas));
        }

        return new MapaAsientosDTO(
            eventoId,
            evento.getFilaAsientos(),
            evento.getColumnAsientos(),
            filas
        );
    }
}

