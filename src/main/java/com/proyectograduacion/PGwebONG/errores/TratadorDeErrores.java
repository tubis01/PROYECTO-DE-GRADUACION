package com.proyectograduacion.PGwebONG.errores;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> manejarError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DatosErrorValidacion>> manejarError400(MethodArgumentNotValidException e) {
        var errores = e.getBindingResult().getFieldErrors().stream()
                .map(DatosErrorValidacion::new)
                .toList();
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(validacionDeIntegridad.class)
    public ResponseEntity<String> manejarErrorValidacionDeIntegridad(validacionDeIntegridad e) {
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
    /////////////////////////////////////////////////////////////////////////

//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity trataerErrores404(){
//        return ResponseEntity.notFound().build();
//    }
//
////    trater erroes 500
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity tratarErrores400(MethodArgumentNotValidException e) {
//        var errores  = e.getBindingResult().getFieldErrors().stream()
//                .map(DatosErrorValidacion::new)
//                .toList();
//        return ResponseEntity.badRequest().body(errores);
//    }
//
//    @ExceptionHandler(validacionDeIntegridad.class)
//    public ResponseEntity erroHandlerValidacionDeIntegridad(Exception e) {
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }
//
//    @ExceptionHandler(ValidationException.class)
//    public ResponseEntity erroHandlerValidacionDeNegocio(Exception e) {
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }
//
//    @ExceptionHandler(NullPointerException.class)
//    public ResponseEntity erroHandlerValidacionActivoInactivo(Exception e) {
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }
//
//    private record  DatosErrorValidacion(String campo, String mensaje){
//        public DatosErrorValidacion(FieldError error){
//            this(error.getField(), error.getDefaultMessage());
//        }
//    }
}
