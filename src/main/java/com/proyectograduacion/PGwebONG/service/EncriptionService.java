package com.proyectograduacion.PGwebONG.service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class EncriptionService {

    // Clave secreta para AES-256 (debe ser exactamente la misma que en el frontend)
    private static final String SECRET_KEY = "myesbale12345678901234567890123456";  // Clave AES de 32 bytes

    public String decryptData(String encryptedData) throws Exception {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

        // Inicializar el cifrador AES en modo ECB con PKCS5Padding
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        // Decodificar los datos de Base64 y desencriptar
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);  // Convertir los bytes desencriptados a cadena UTF-8
    }
}

