package com.proyectograduacion.PGwebONG.domain.usuarios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String usuario;

    private String clave;

    @Column(name = "tokenpassword")
    private String tokenPassword;

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


}
