package com.mycompany.myapp.filtro;
import com.mycompany.myapp.service.impl.SesionRedisManager;
import com.mycompany.myapp.service.impl.SesionServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class SessionFilter extends OncePerRequestFilter {


    private final SesionServiceImpl sesionServiceImpl;

    private final SesionRedisManager sesionRedisManager;


    public SessionFilter(SesionServiceImpl sesionServiceImpl, SesionRedisManager sesionRedisManager) {
        this.sesionServiceImpl = sesionServiceImpl;
        this.sesionRedisManager = sesionRedisManager;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.startsWith("/api/authenticate")
            || path.startsWith("/api/internal/")
            || path.startsWith("/h2-console")
            || path.startsWith("/actuator/")
            || path.startsWith("/swagger")
            || path.startsWith("/v3/api-docs")
            || path.startsWith("/error");
    }



    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws IOException, ServletException {

        String token = request.getHeader("X-Session-Token");

        if (token == null || sesionServiceImpl.renovarActividad(token) == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Sesion expirada o inválida\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
