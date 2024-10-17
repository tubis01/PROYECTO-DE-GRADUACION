package com.proyectograduacion.PGwebONG;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.security.SecureRandom;
import java.util.Base64;

@SpringBootApplication
public class PGwebOngApplication {

	public static void main(String[] args) {
		SpringApplication.run(PGwebOngApplication.class, args);


	}
}
