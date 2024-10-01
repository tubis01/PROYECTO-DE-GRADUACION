package com.proyectograduacion.PGwebONG.controller;

import com.proyectograduacion.PGwebONG.domain.usuarios.DatosAutenticacionUsuario;
import com.proyectograduacion.PGwebONG.domain.usuarios.UsuarioPrincipal;
import com.proyectograduacion.PGwebONG.infra.security.DatosJWTToken;
import com.proyectograduacion.PGwebONG.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public AutenticacionController(AuthenticationManager manager, TokenService tokenService) {
        this.authenticationManager = manager;
        this.tokenService = tokenService;
    }


//    @PostMapping
//    public ResponseEntity<DatosJWTToken> autorizarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datos){
//        Authentication authToken = new UsernamePasswordAuthenticationToken(datos.usuario(), datos.contrasena());
//        Authentication usuarioAutorizado = authenticationManager.authenticate(authToken);
//        String jwtToken = tokenService.generarToken((Usuario) usuarioAutorizado.getPrincipal());
//        return ResponseEntity.ok(new DatosJWTToken(jwtToken));
//    }

    @PostMapping
    public ResponseEntity<DatosJWTToken> login (@RequestBody  @Valid DatosAutenticacionUsuario datos){
        Authentication authToken = new UsernamePasswordAuthenticationToken(datos.usuario(), datos.contrasena());
        Authentication usuarioAutorizado = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(usuarioAutorizado);
        String jwtToken = tokenService.generarToken( usuarioAutorizado);


        UsuarioPrincipal usuario = (UsuarioPrincipal)  usuarioAutorizado.getPrincipal();
        List<String> roles = usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();


        return ResponseEntity.ok(new DatosJWTToken(jwtToken, usuario.getUsuario(), roles));

    }

}