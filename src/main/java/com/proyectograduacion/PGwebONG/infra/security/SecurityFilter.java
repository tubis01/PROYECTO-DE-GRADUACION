package com.proyectograduacion.PGwebONG.infra.security;

import com.proyectograduacion.PGwebONG.domain.usuarios.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter  extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final AutenticacionService autenticacionService;


    public SecurityFilter(TokenService tokenService,
                          AutenticacionService autenticacionService) {
        this.tokenService = tokenService;
        this.autenticacionService = autenticacionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String autHeader = request.getHeader("Authorization");
        if (autHeader != null) {
            String token = autHeader.replace("Bearer ", "");
            String subject = tokenService.getSubject(token);
            if (subject != null) {
                UserDetails usuario = autenticacionService.loadUserByUsername(subject);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken
                        (usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            System.out.println(token);
            System.out.println(tokenService.getSubject(token));
        }
        filterChain.doFilter(request, response);
    }
}
