package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Asientos;
import com.mycompany.myapp.repository.AsientosRepository;
import com.mycompany.myapp.service.AsientosService;
import com.mycompany.myapp.service.dto.AsientosDTO;
import com.mycompany.myapp.service.mapper.AsientosMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Asientos}.
 */
@Service
@Transactional
public class AsientosServiceImpl implements AsientosService {

    private static final Logger LOG = LoggerFactory.getLogger(AsientosServiceImpl.class);

    private final AsientosRepository asientosRepository;

    private final AsientosMapper asientosMapper;

    public AsientosServiceImpl(AsientosRepository asientosRepository, AsientosMapper asientosMapper) {
        this.asientosRepository = asientosRepository;
        this.asientosMapper = asientosMapper;
    }

    @Override
    public AsientosDTO save(AsientosDTO asientosDTO) {
        LOG.debug("Request to save Asientos : {}", asientosDTO);
        Asientos asientos = asientosMapper.toEntity(asientosDTO);
        asientos = asientosRepository.save(asientos);
        return asientosMapper.toDto(asientos);
    }

    @Override
    public AsientosDTO update(AsientosDTO asientosDTO) {
        LOG.debug("Request to update Asientos : {}", asientosDTO);
        Asientos asientos = asientosMapper.toEntity(asientosDTO);
        asientos = asientosRepository.save(asientos);
        return asientosMapper.toDto(asientos);
    }

    @Override
    public Optional<AsientosDTO> partialUpdate(AsientosDTO asientosDTO) {
        LOG.debug("Request to partially update Asientos : {}", asientosDTO);

        return asientosRepository
            .findById(asientosDTO.getId())
            .map(existingAsientos -> {
                asientosMapper.partialUpdate(existingAsientos, asientosDTO);

                return existingAsientos;
            })
            .map(asientosRepository::save)
            .map(asientosMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsientosDTO> findAll() {
        LOG.debug("Request to get all Asientos");
        return asientosRepository.findAll().stream().map(asientosMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AsientosDTO> findOne(Long id) {
        LOG.debug("Request to get Asientos : {}", id);
        return asientosRepository.findById(id).map(asientosMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Asientos : {}", id);
        asientosRepository.deleteById(id);
    }
}
