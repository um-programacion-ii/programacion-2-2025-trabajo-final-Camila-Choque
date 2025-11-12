package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Integrantes;
import com.mycompany.myapp.repository.IntegrantesRepository;
import com.mycompany.myapp.service.IntegrantesService;
import com.mycompany.myapp.service.dto.IntegrantesDTO;
import com.mycompany.myapp.service.mapper.IntegrantesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Integrantes}.
 */
@Service
@Transactional
public class IntegrantesServiceImpl implements IntegrantesService {

    private static final Logger LOG = LoggerFactory.getLogger(IntegrantesServiceImpl.class);

    private final IntegrantesRepository integrantesRepository;

    private final IntegrantesMapper integrantesMapper;

    public IntegrantesServiceImpl(IntegrantesRepository integrantesRepository, IntegrantesMapper integrantesMapper) {
        this.integrantesRepository = integrantesRepository;
        this.integrantesMapper = integrantesMapper;
    }

    @Override
    public IntegrantesDTO save(IntegrantesDTO integrantesDTO) {
        LOG.debug("Request to save Integrantes : {}", integrantesDTO);
        Integrantes integrantes = integrantesMapper.toEntity(integrantesDTO);
        integrantes = integrantesRepository.save(integrantes);
        return integrantesMapper.toDto(integrantes);
    }

    @Override
    public IntegrantesDTO update(IntegrantesDTO integrantesDTO) {
        LOG.debug("Request to update Integrantes : {}", integrantesDTO);
        Integrantes integrantes = integrantesMapper.toEntity(integrantesDTO);
        integrantes = integrantesRepository.save(integrantes);
        return integrantesMapper.toDto(integrantes);
    }

    @Override
    public Optional<IntegrantesDTO> partialUpdate(IntegrantesDTO integrantesDTO) {
        LOG.debug("Request to partially update Integrantes : {}", integrantesDTO);

        return integrantesRepository
            .findById(integrantesDTO.getId())
            .map(existingIntegrantes -> {
                integrantesMapper.partialUpdate(existingIntegrantes, integrantesDTO);

                return existingIntegrantes;
            })
            .map(integrantesRepository::save)
            .map(integrantesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IntegrantesDTO> findAll() {
        LOG.debug("Request to get all Integrantes");
        return integrantesRepository.findAll().stream().map(integrantesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IntegrantesDTO> findOne(Long id) {
        LOG.debug("Request to get Integrantes : {}", id);
        return integrantesRepository.findById(id).map(integrantesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Integrantes : {}", id);
        integrantesRepository.deleteById(id);
    }
}
