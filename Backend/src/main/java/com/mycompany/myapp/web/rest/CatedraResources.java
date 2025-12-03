package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.service.CatedraServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// Dieccion con la que el frontend se comunica con el back
@RequestMapping("/catedra")
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
}
