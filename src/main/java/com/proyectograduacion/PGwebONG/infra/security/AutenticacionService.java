package com.proyectograduacion.PGwebONG.infra.security;

import com.proyectograduacion.PGwebONG.domain.usuarios.Usuario;
import com.proyectograduacion.PGwebONG.domain.usuarios.UsuarioPrincipal;
import com.proyectograduacion.PGwebONG.domain.usuarios.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AutenticacionService implements UserDetailsService {

    private final UsuarioRepository usuarioRepo;

    public AutenticacionService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByUsuarioOrEmail(username, username);
        if (!usuario.isActivo()) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return UsuarioPrincipal.build(usuario);
    }
}
