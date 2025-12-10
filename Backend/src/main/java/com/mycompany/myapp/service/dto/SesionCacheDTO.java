package com.mycompany.myapp.service.dto;
import java.io.Serializable;

public class SesionCacheDTO implements Serializable {

    private String token;
    private Long sesionId;
    private Long usuarioId;

    public SesionCacheDTO() {}

    public SesionCacheDTO(String token, Long sesionId, Long usuarioId) {
        this.token = token;
        this.sesionId = sesionId;
        this.usuarioId = usuarioId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getSesionId() {
        return sesionId;
    }

    public void setSesionId(Long sesionId) {
        this.sesionId = sesionId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
