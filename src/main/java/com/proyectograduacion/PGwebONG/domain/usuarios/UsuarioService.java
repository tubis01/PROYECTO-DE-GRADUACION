package com.proyectograduacion.PGwebONG.domain.usuarios;

import com.proyectograduacion.PGwebONG.emialPassword.dto.ChangePasswordDTO;
import com.proyectograduacion.PGwebONG.infra.errores.validacionDeIntegridad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository,
                          BCryptPasswordEncoder cryptPasswordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.bCryptPasswordEncoder = cryptPasswordEncoder;
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
        if(datosRegistroUsuario.rol().contains("admin")){
            roles.add(rolRepository.findByNombre(RolNombre.ROLE_ADMIN));
        } else if (datosRegistroUsuario.rol().contains("digitador")) {
            roles.add(rolRepository.findByNombre(RolNombre.ROLE_DIGITADOR));
        }else if (datosRegistroUsuario.rol().contains("user")) {
            roles.add(rolRepository.findByNombre(RolNombre.ROLE_USER));
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
//TODO: implementacion de cambio de clave
    public Optional<Usuario> getByNombreUsuarioOrEmail(String nameOrEmail){
        return Optional.ofNullable(usuarioRepository.findByUsuarioOrEmail(nameOrEmail, nameOrEmail));
    }

    public Optional<Usuario> getByTokenPassword(String tokenPassword){
        return usuarioRepository.findByTokenPassword(tokenPassword);
    }

    public String generarTokenPassword(Usuario usuario){
        UUID uuid = UUID.randomUUID();
        String tokenPassword = uuid.toString();
        usuario.setTokenPassword(tokenPassword);
        usuarioRepository.save(usuario);
        return tokenPassword;
    }

    public boolean cambiarClave(ChangePasswordDTO dto){
        Optional<Usuario> usuarioOpt = getByTokenPassword(dto.getTokenPassword());
        if(usuarioOpt.isEmpty()){
            return false;
        }
        Usuario usuario = usuarioOpt.get();
        String nuveClave= bCryptPasswordEncoder.encode(dto.getPassword());
        usuario.setClave(nuveClave);
        usuario.setTokenPassword(null);
        usuarioRepository.save(usuario);
        return true;
    }

    public void deshabilitarUsuario(Long id){
        Usuario usuario = verificarExistenciaUsuario(id);
        usuario.deshabilitarUsuario();
    }


    /*
    * Método que verifica la existencia de un usuario
     */

    private Usuario verificarExistenciaUsuario(Long id) {
        if(!usuarioRepository.existsById(id)){
            throw new validacionDeIntegridad("No existe el usuario");
        }
        return usuarioRepository.getReferenceById(id);
    }
//    verificar usuario e email
    private void verificarUsuarioYEmail(String usuario, String email){
        if(usuarioRepository.existsByEmail(email)){
            throw new validacionDeIntegridad("Ya existe el {{ email }}, por favor ingrese otro");
        }
        if(usuarioRepository.existsByUsuario(usuario)){
            throw new validacionDeIntegridad("Ya existe un {{ usuario }} con ese nombre, por favor ingrese otro");
        }
    }


}
