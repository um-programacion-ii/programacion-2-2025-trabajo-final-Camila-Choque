package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Sesion;
import com.mycompany.myapp.repository.SesionRepository;
import com.mycompany.myapp.service.SesionService;
import com.mycompany.myapp.service.dto.SesionCacheDTO;
import com.mycompany.myapp.service.dto.SesionDTO;
import com.mycompany.myapp.service.mapper.SesionMapper;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private SesionRedisManager sesionRedisManager;


    public SesionServiceImpl(SesionRepository sesionRepository, SesionMapper sesionMapper, SesionRedisManager sesionRedisManager) {
        this.sesionRepository = sesionRepository;
        this.sesionMapper = sesionMapper;
        this.sesionRedisManager = sesionRedisManager;
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
    public Sesion crearSesion(Long usuarioId) {
        Sesion sesion = new Sesion();
        sesion.setToken(UUID.randomUUID().toString());
        sesion.setEstadoFlujo("INICIO");
        sesion.setUltimaActividad(LocalDate.now());
        sesion.setExpiraEn(LocalDate.now().plusDays(1)); // ejemplo
        sesionRepository.save(sesion);

        SesionCacheDTO cache = new SesionCacheDTO(
            sesion.getToken(),
            sesion.getId(),
            usuarioId
        );

        sesionRedisManager.guardarEnCache(cache);

        return sesion;
    }

    public Sesion renovarActividad(String token) {
        SesionCacheDTO dto = sesionRedisManager.obtenerDeCache(token);
        if (dto == null) return null;

        Sesion sesion = sesionRepository.findById(dto.getSesionId()).orElse(null);
        if (sesion == null) return null;

        sesion.setUltimaActividad(LocalDate.now());
        sesionRepository.save(sesion);

        return sesion;
    }

    public void logout(String token) {
        SesionCacheDTO dto = sesionRedisManager.obtenerDeCache(token);
        if (dto != null) {
            sesionRedisManager.borrar(token);
        }
    }
}
