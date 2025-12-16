package com.mycompany.myapp.service.impl;
import com.mycompany.myapp.domain.Asientos;
import com.mycompany.myapp.repository.AsientosRepository;
import com.mycompany.myapp.service.CatedraServices;
import com.mycompany.myapp.service.dto.AsientosDisponiblesDTO;
import com.mycompany.myapp.service.dto.AsientosRedisDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AsientosDisponiblesService {

    private final AsientosRepository asientosRepository;
    private final CatedraServices catedraServices;

    public AsientosDisponiblesService(
        AsientosRepository asientosRepository,
        CatedraServices catedraServices) {

        this.asientosRepository = asientosRepository;
        this.catedraServices = catedraServices;
    }

    public List<AsientosDisponiblesDTO> obtenerAsientosDisponibles(Long eventoId) {


        List<Asientos> asientosBD =
            asientosRepository.findByEventoId(eventoId);

        AsientosRedisDTO redisDTO =
            catedraServices.getAsientos(eventoId);

        Set<String> noDisponibles = redisDTO.getAsientos().stream()
            .filter(a ->
                "Bloqueado".equalsIgnoreCase(a.getEstado()) ||
                    "Vendido".equalsIgnoreCase(a.getEstado())
            )
            .map(a -> a.getFila() + "-" + a.getColumna())
            .collect(Collectors.toSet());
        return asientosBD.stream()
            .filter(a ->
                !noDisponibles.contains(
                    a.getFila() + "-" + a.getColumna()
                )
            )
            .map(a ->
                new AsientosDisponiblesDTO(
                    a.getFila(),
                    a.getColumna()
                )
            )
            .toList();
    }
}

