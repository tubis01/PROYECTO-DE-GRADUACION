package com.proyectograduacion.PGwebONG.emialPassword.controller;

import com.proyectograduacion.PGwebONG.domain.usuarios.Usuario;
import com.proyectograduacion.PGwebONG.domain.usuarios.UsuarioService;
import com.proyectograduacion.PGwebONG.emialPassword.dto.ChangePasswordDTO;
import com.proyectograduacion.PGwebONG.emialPassword.dto.EmailValuesDTO;
import com.proyectograduacion.PGwebONG.emialPassword.dto.Mensaje;
import com.proyectograduacion.PGwebONG.emialPassword.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String mailFrom;

    public EmailController(EmailService emailService, UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.emailService = emailService;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/send-html")
    public ResponseEntity<?> senEmailHtml(@RequestBody EmailValuesDTO dto){
        emailService.sendEmail(dto);
        return new ResponseEntity<>("correo envia plantailla", HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailValuesDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioService.getByNombreUsuarioOrEmail(dto.getMailTo());
        if(usuarioOpt.isEmpty())
            return new ResponseEntity<>(new Mensaje("No existe ningún usuario con esas credenciales"), HttpStatus.NOT_FOUND);
        Usuario usuario = usuarioOpt.get();
        dto.setMailFrom(mailFrom);
        dto.setMailTo(usuario.getEmail());
        dto.setSubject("Cambio de Contraseña");
        dto.setUserName(usuario.getUsuario());

        String tokenPassword = usuarioService.generarTokenPassword(usuario);
        dto.setTokenPassword(tokenPassword);

        emailService.sendEmail(dto);
        return new ResponseEntity<>(new Mensaje("Te hemos enviado un correo"), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO passwordDTO,
                                            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(new Mensaje("campos mal puestos o email invalido"), HttpStatus.BAD_REQUEST);
        }
        if(!passwordDTO.getPassword().equals(passwordDTO.getConfirmPassword())){
            return new ResponseEntity<>(new Mensaje("Las contraseñas no coinciden"), HttpStatus.BAD_REQUEST);
        }
        boolean uptaded = usuarioService.cambiarClave(passwordDTO);

        if(!uptaded){
            return new ResponseEntity<>(new Mensaje("No se ha podido cambiar la contraseña"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new Mensaje("Contraseña cambiada"), HttpStatus.OK);
    }

}
