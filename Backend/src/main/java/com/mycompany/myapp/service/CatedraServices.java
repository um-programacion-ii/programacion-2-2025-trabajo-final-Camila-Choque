package com.mycompany.myapp.service;
import com.mycompany.myapp.service.client.ProxyClient;
import org.springframework.stereotype.Service;

@Service
public class CatedraServices {
    private final ProxyClient proxyClient;

    public CatedraServices(ProxyClient proxyClient) {
        this.proxyClient = proxyClient;
    }

    public String registrar(String body) {
        return proxyClient.registrar(body);
    }

}
