package com.proyectograduacion.PGwebONG.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.proyectograduacion.PGwebONG.domain.usuarios.UsuarioPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    private static final int TOKE_DURATION_HOURS = 2;


    public String generarToken(Authentication usuario) {
        try {
            UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal)  usuario.getPrincipal();

            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            List<String> roles = usuarioPrincipal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            return JWT.create()
                    .withIssuer("PGwebONG")
                    .withSubject(usuarioPrincipal.getUsuario())
                    .withClaim("roles", roles)
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error al crear el token");
        }
    }

        public String getSubject(String token){
            DecodedJWT verifier;

            try {
                verifier = JWT.require(Algorithm.HMAC256(apiSecret))
                        .withIssuer("PGwebONG")
                        .build()
                        .verify(token);
                verifier.getSubject();
            } catch (Exception e) {
                throw new RuntimeException("Token no valido");
            }

            if(verifier.getSubject() == null){
                throw new RuntimeException("token invalido");
            }
            return verifier.getSubject();
        }

        private Instant generarFechaExpiracion(){
        return LocalDateTime.now().plusHours(TOKE_DURATION_HOURS)
                .toInstant(ZoneOffset.of("-05:00"));
//            return LocalDate.now().plusWeeks(1).atStartOfDay().toInstant(ZoneOffset.UTC)    ;
        }
    }

