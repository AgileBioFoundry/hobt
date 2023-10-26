package org.abf.hobt.common.util;

import org.abf.hobt.common.exception.UtilityException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

/**
 * Utility class for handling account passwords
 *
 * @author Hector Plahar
 */
public class PasswordUtil {

    private static final int HASH_BYTE_SIZE = 160;
    private static final int SALT_BYTE_SIZE = 32;
    private static final int PBKDF2_ITERATIONS = 20000;
    private static final int TOKEN_BYTE_SIZE = 128;
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_XTERS = "!@#$%^&*()_+";

    public static String encryptPassword(String password, String salt) throws UtilityException {
        if (password == null || password.trim().isEmpty() || salt == null || salt.trim().isEmpty())
            throw new NullPointerException("Password and/or salt cannot be empty");

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), PBKDF2_ITERATIONS, HASH_BYTE_SIZE);

        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = keyFactory.generateSecret(spec).getEncoded();
            return Arrays.toString(Hex.encodeHex(hash));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new UtilityException(e);
        }
    }

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        return Arrays.toString(Hex.encodeHex(salt));
    }

    public static String generateTemporaryPassword(int length) {
        String charactersForPassword = LOWER.toUpperCase() + LOWER + DIGITS + SPECIAL_XTERS;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charactersForPassword.length());
            password.append(charactersForPassword.charAt(index));
        }

        return password.toString();

//        char[] arr = UUID.randomUUID().toString().substring(24).toCharArray();
//        boolean converted = false;
//        for (int i = 0; i < arr.length; i += 1) {
//            if (arr[i] >= 'a' && arr[i] <= 'z') {
//                arr[i] = (char) (arr[i] - 32);
//                if (converted)
//                    break;
//                converted = true;
//            }
//        }
//        return String.copyValueOf(arr);
    }

    public static String generateRandomToken() {
        SecureRandom random = new SecureRandom();
        byte[] token = new byte[TOKEN_BYTE_SIZE];
        random.nextBytes(token);
        return Base64.getEncoder().encodeToString(token);
    }

    public static String generateRandomToken(int byteSize) {
        SecureRandom random = new SecureRandom();
        byte[] token = new byte[byteSize];
        random.nextBytes(token);
        return Base64.getEncoder().encodeToString(token);
    }
}
