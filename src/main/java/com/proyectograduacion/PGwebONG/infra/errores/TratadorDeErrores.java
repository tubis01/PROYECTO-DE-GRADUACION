package com.proyectograduacion.PGwebONG.infra.errores;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DatosErrorValidacion>> manejarError400(MethodArgumentNotValidException e) {
        var errores = e.getBindingResult().getFieldErrors().stream()
                .map(DatosErrorValidacion::new)
                .toList();
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(validacionDeIntegridad.class)
    public ResponseEntity<String> manejarErrorValidacionDeIntegridad(validacionDeIntegridad e) {
        if (e.getMessage().contains("No existe")) {
            // Cuando la persona no se encuentra (404 Not Found)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } else if (e.getMessage().contains("Ya existe")) {
            // Cuando hay un conflicto de registro (409 Conflict)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } else if (e.getMessage().contains("inactivos")) {
        // Cuando se intenta registrar un beneficiario inactivo (400 Bad Request)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
    }
        // Para otros casos, podría ser un bad request genérico (400 Bad Request)
        return ResponseEntity.badRequest().body(e.getMessage());
    }



    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> manejarErrorValidacionDeNegocio(ValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> manejarErrorNullPointer(NullPointerException e) {
        return ResponseEntity.badRequest().body("Se produjo un error inesperado: " + e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarErrorArgumentoIlegal(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("Argumento no válido: " + e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> manejarErrorMensajeNoLegible(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body("Mensaje no legible: " + e.getMessage());
    }

//    @ControllerAdvice
//    public static class GlobalExceptionHandler {
//        @ExceptionHandler(Exception.class)
//        public ResponseEntity<String> handleException(Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> manejarErrorAccesoDenegado(AccessDeniedException e) {
        return new ResponseEntity<>("Acceso denegado: " + e.getMessage(), HttpStatus.FORBIDDEN);
    }
}
