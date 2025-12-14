package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Evento;
import com.mycompany.myapp.repository.EventoRepository;
import com.mycompany.myapp.service.EventoService;
import com.mycompany.myapp.service.dto.EventoDTO;
import com.mycompany.myapp.service.mapper.EventoMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Evento}.
 */
@Service
@Transactional
public class EventoServiceImpl implements EventoService {

    private static final Logger LOG = LoggerFactory.getLogger(EventoServiceImpl.class);

    private final EventoRepository eventoRepository;

    private final EventoMapper eventoMapper;

    public EventoServiceImpl(EventoRepository eventoRepository, EventoMapper eventoMapper) {
        this.eventoRepository = eventoRepository;
        this.eventoMapper = eventoMapper;
    }

    @Override
    public EventoDTO save(EventoDTO eventoDTO) {
        LOG.debug("Request to save Evento : {}", eventoDTO);
        Evento evento = eventoMapper.toEntity(eventoDTO);
        evento.ultimaActualizacion(LocalDate.now());
        evento = eventoRepository.save(evento);
        return eventoMapper.toDto(evento);
    }

    @Override
    public EventoDTO update(EventoDTO eventoDTO) {
        LOG.debug("Request to update Evento : {}", eventoDTO);
        Evento evento = eventoMapper.toEntity(eventoDTO);
        evento = eventoRepository.save(evento);
        return eventoMapper.toDto(evento);
    }

    @Override
    public Optional<EventoDTO> partialUpdate(EventoDTO eventoDTO) {
        LOG.debug("Request to partially update Evento : {}", eventoDTO);

        return eventoRepository
            .findById(eventoDTO.getId())
            .map(existingEvento -> {
                eventoMapper.partialUpdate(existingEvento, eventoDTO);

                return existingEvento;
            })
            .map(eventoRepository::save)
            .map(eventoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> findAll() {
        LOG.debug("Request to get all Eventos");
        return eventoRepository.findAll().stream().map(eventoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventoDTO> findOne(Long id) {
        LOG.debug("Request to get Evento : {}", id);
        return eventoRepository.findById(id).map(eventoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Evento : {}", id);
        eventoRepository.deleteById(id);
    }
}
