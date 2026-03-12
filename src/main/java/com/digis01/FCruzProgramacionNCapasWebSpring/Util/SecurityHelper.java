package com.digis01.FCruzProgramacionNCapasWebSpring.Util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class SecurityHelper {

    private static final String LLAVE_SECRETA = "ClaveMasiva_2026"; 
    private static final String ALGORITMO = "AES";

    public static String encriptarAES(String data) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(LLAVE_SECRETA.getBytes(), ALGORITMO);
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encriptado = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encriptado);
        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar: " + e.getMessage());
        }
    }

    public static String desencriptarAES(String dataEncriptada) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(LLAVE_SECRETA.getBytes(), ALGORITMO);
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] desencriptado = cipher.doFinal(Base64.getDecoder().decode(dataEncriptada));
            return new String(desencriptado);
        } catch (Exception e) {
            throw new RuntimeException("Error al desencriptar la Key: " + e.getMessage());
        }
    }
}
