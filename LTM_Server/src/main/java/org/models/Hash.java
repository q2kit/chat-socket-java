package org.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    public static String hash(String input, String secretKey) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(secretKey.getBytes());
            byte[] bytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String hash(String input) {
        return hash(input, "default");
    }

    public static boolean verify(String input, String secretKey, String hash) {
        return hash(input, secretKey).equals(hash);
    }

    public static boolean verify(String input, String hash) {
        return verify(input, "default", hash);
    }
}
