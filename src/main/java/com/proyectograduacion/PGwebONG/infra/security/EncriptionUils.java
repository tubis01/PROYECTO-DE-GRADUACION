package com.proyectograduacion.PGwebONG.infra.security;

import org.springframework.beans.factory.annotation.Value;

public class EncriptionUils {

    @Value("${encryption.key}")
    private  String PASSWORD ;

    @Value("${encryption.salt}")
    private  String SALT;


}


