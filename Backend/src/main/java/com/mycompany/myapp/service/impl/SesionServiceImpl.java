package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Sesion;
import com.mycompany.myapp.repository.SesionRepository;
import com.mycompany.myapp.service.SesionService;
import com.mycompany.myapp.service.dto.SesionDTO;
import com.mycompany.myapp.service.mapper.SesionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Sesion}.
 */
@Service
@Transactional
public class SesionServiceImpl implements SesionService {

    private static final Logger LOG = LoggerFactory.getLogger(SesionServiceImpl.class);

    private final SesionRepository sesionRepository;

    private final SesionMapper sesionMapper;

    public SesionServiceImpl(SesionRepository sesionRepository, SesionMapper sesionMapper) {
        this.sesionRepository = sesionRepository;
        this.sesionMapper = sesionMapper;
    }

    @Override
    public SesionDTO save(SesionDTO sesionDTO) {
        LOG.debug("Request to save Sesion : {}", sesionDTO);
        Sesion sesion = sesionMapper.toEntity(sesionDTO);
        sesion = sesionRepository.save(sesion);
        return sesionMapper.toDto(sesion);
    }

    @Override
    public SesionDTO update(SesionDTO sesionDTO) {
        LOG.debug("Request to update Sesion : {}", sesionDTO);
        Sesion sesion = sesionMapper.toEntity(sesionDTO);
        sesion = sesionRepository.save(sesion);
        return sesionMapper.toDto(sesion);
    }

    @Override
    public Optional<SesionDTO> partialUpdate(SesionDTO sesionDTO) {
        LOG.debug("Request to partially update Sesion : {}", sesionDTO);

        return sesionRepository
            .findById(sesionDTO.getId())
            .map(existingSesion -> {
                sesionMapper.partialUpdate(existingSesion, sesionDTO);

                return existingSesion;
            })
            .map(sesionRepository::save)
            .map(sesionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SesionDTO> findAll() {
        LOG.debug("Request to get all Sesions");
        return sesionRepository.findAll().stream().map(sesionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SesionDTO> findOne(Long id) {
        LOG.debug("Request to get Sesion : {}", id);
        return sesionRepository.findById(id).map(sesionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Sesion : {}", id);
        sesionRepository.deleteById(id);
    }
}
