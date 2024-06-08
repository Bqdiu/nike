package com.example.nike;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class OTPGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateOTP(int length) {
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < length; i++) {
            otp.append(secureRandom.nextInt(10));
        }

        return otp.toString();
    }

    public static void main(String[] args) {
        int length = 6;
        String otp1 = generateOTP(length);
        String otp2 = generateOTP(length);
        System.out.println("Generated OTP 1: " + otp1);
        System.out.println("Generated OTP 2: " + otp2);
    }

}
