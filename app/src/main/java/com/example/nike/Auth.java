package com.example.nike;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Auth {
    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        BigInteger bigInt = new BigInteger(1, hash);
        String hashedPassword = bigInt.toString(16);
        while (hashedPassword.length() < 32) {
            hashedPassword = "0" + hashedPassword;
        }
        return hashedPassword;
    }
    public boolean checkPassword(String enteredPassword, String storedPassword) throws NoSuchAlgorithmException {
        String hashedEnteredPassword = hashPassword(enteredPassword);
        return hashedEnteredPassword.equals(storedPassword);
    }
}
