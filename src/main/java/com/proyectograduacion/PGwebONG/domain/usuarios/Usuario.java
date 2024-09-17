package com.proyectograduacion.PGwebONG.domain.usuarios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String usuario;

    private String clave;

    private boolean activo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_roles",
            joinColumns =  @JoinColumn(name= "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Set<Rol> roles= new HashSet<>();

    public Usuario (DatosRegistroUsuario datosRegistroUsuario,
                    BCryptPasswordEncoder bCryptPasswordEncoder){
        this.email = datosRegistroUsuario.email();
        this.usuario = datosRegistroUsuario.usuario();
        this.clave = bCryptPasswordEncoder.encode(datosRegistroUsuario.clave());
        this.activo = true;
    }

    public void actualizarUsuario(DatosActualizarUsuario datosActualizarUsuario,
                                  BCryptPasswordEncoder bCryptPasswordEncoder){
        if(datosActualizarUsuario.email() != null){
            this.email = datosActualizarUsuario.email();
        }
        if(datosActualizarUsuario.clave() != null){
            this.clave = bCryptPasswordEncoder.encode(datosActualizarUsuario.clave());
        }
    }

    public void deshabilitarUsuario() {
        this.activo = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombre().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return clave;
    }

    @Override
    public String getUsername() {
        return usuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
