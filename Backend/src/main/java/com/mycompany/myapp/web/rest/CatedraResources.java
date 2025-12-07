package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.service.CatedraServices;
import com.mycompany.myapp.service.dto.EventoDTO;
import com.mycompany.myapp.service.dto.EventoResumidoDTO;
import jakarta.annotation.security.PermitAll;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
// Dieccion con la que el frontend se comunica con el back
@RequestMapping("/api/catedra")
@PermitAll
public class CatedraResources {
    private final CatedraServices catedraServices;

    public CatedraResources(CatedraServices catedraServices) {
        this.catedraServices = catedraServices;
    }

    //Si nos queremos registrar hay que hacer un post a /catedra/registrar
    @PostMapping("/registrar")
    public String registrar(@RequestBody String body) {
        return catedraServices.registrar(body);
    }

    @GetMapping("eventos/{id}")
    public EventoDTO conseguirEvento(@PathVariable Long id) {
        return catedraServices.conseguirEventoPorId(id.toString());
    }
    @GetMapping("/eventos")
    public List<EventoDTO> conseguirEventos() {
        return catedraServices.conseguirEventos();
    }
    @GetMapping("/eventos-resumidos")
    public List<EventoResumidoDTO> conseguirEventosResumidos() {
        return catedraServices.conseguirEventosResumidos();
    }

}
