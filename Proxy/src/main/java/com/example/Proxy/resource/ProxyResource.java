package com.example.Proxy.resource;
import com.example.Proxy.dto.RegistrarUsuarioDto;
import com.example.Proxy.dto.RegistrarUsuarioResponse;
import com.example.Proxy.services.ProxyServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy")
public class ProxyResource {
    private final ProxyServices proxyService;

    public ProxyResource(ProxyServices proxyService) {
        this.proxyService = proxyService;
    }

    @PostMapping("/registrar")
    public RegistrarUsuarioResponse registrarUsuario(@RequestBody RegistrarUsuarioDto dto) {
        return proxyService.registarUsuario(dto);
    }
}