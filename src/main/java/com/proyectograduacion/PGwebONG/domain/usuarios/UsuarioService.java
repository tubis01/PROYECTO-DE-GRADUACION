package com.proyectograduacion.PGwebONG.domain.usuarios;

import com.proyectograduacion.PGwebONG.infra.errores.validacionDeIntegridad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }
    /*
    * Método que lista los usuarios activos
     */
    public Page<DatosDetalleUsuario> listarUsuariosActivos(Pageable pageable){
        return usuarioRepository.findByActivoTrue(pageable)
                .map(DatosDetalleUsuario::new);
    }

    /*
    * Método que lista los usuarios inactivos
     */
    public Page<DatosDetalleUsuario> listarUsuariosInactivos(Pageable pageable){
        return usuarioRepository.findByActivoFalse(pageable)
                .map(DatosDetalleUsuario::new);
    }

    /*
    * Método que obtiene un usuario por su id
     */

    public Usuario obtenerUsuarioPorId(Long id){
        return verificarExistenciaUsuario(id);
    }

/*
* Método que registra un usuario
 */
    public Usuario registrarUsuario (DatosRegistroUsuario datosRegistroUsuario) {
        verificarUsuarioYEmail(datosRegistroUsuario.usuario(), datosRegistroUsuario.email());
        Usuario usuario = new Usuario(datosRegistroUsuario, new BCryptPasswordEncoder());

        Set<Rol> roles = new HashSet<>();
        roles.add(rolRepository.findByNombre(RolNombre.ROLE_USER));
        if(datosRegistroUsuario.rol().contains("admin")){
            roles.add(rolRepository.findByNombre(RolNombre.ROLE_ADMIN));
        }
        usuario.setRoles(roles);
        usuarioRepository.save(usuario);
        return usuario;
    }

    /*
    * Método que modifica un usuario
     */
    public Usuario actualizarUsuario(DatosActualizarUsuario datosActualizarUsuario){
        Usuario usuario = verificarExistenciaUsuario(datosActualizarUsuario.id());
        usuario.actualizarUsuario(datosActualizarUsuario, new BCryptPasswordEncoder());
        return usuario;
    }

    /*
    * Método que deshabilita un usuario
     */
    public void deshabilitarUsuario(Long id){
        Usuario usuario = verificarExistenciaUsuario(id);
        usuario.deshabilitarUsuario();
    }


    /*
    * Método que verifica la existencia de un usuario
     */

    private Usuario verificarExistenciaUsuario(Long id) {
        if(!usuarioRepository.existsById(id)){
            throw new validacionDeIntegridad("El usuario no existe");
        }
        return usuarioRepository.getReferenceById(id);
    }
//    verificar usuario e email
    private void verificarUsuarioYEmail(String usuario, String email){
        if(usuarioRepository.existsByEmail(email)){
            throw new validacionDeIntegridad("El correo ya fue registrado");
        }
        if(usuarioRepository.existsByUsuario(usuario)){
            throw new validacionDeIntegridad("El usuario ya fue registrado");
        }
    }
}
