package com.proyectograduacion.PGwebONG.errores;

public class validacionDeIntegridad extends RuntimeException {
    public validacionDeIntegridad(String mensaje) {
        //se utilizar super para llamar al constructor de la clase padre
//        en este caso se llama al constructor de la clase RuntimeException
        super(mensaje);
    }
}
