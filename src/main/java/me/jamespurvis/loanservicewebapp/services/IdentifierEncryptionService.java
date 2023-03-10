package me.jamespurvis.loanservicewebapp.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.Base64;


@Service
public class IdentifierEncryptionService {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String CHARSET_NAME = "UTF-8";
    private static final int KEY_SIZE = 128;

    private final SecretKeySpec secretKeySpec;
    private final Cipher cipher;

    public IdentifierEncryptionService(@Value("${encryption.key}") String encryptionKey) throws Exception {
        byte[] keyBytes = Arrays.copyOf(encryptionKey.getBytes(CHARSET_NAME), KEY_SIZE / 8);
        secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        cipher = Cipher.getInstance(ALGORITHM);
    }

    public String encrypt(String ssn) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(ssn.getBytes(CHARSET_NAME));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedSSN) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedSSN);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, CHARSET_NAME);
    }

}
