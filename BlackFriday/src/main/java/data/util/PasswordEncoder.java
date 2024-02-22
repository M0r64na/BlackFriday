package data.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordEncoder {
    private static final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, 16, 32);

    public static String encodePassword(String password) {
        String encodedPassword = argon2.hash(17, 55000, 1, password.toCharArray());
        argon2.wipeArray(password.toCharArray());

        return encodedPassword;
    }

    public static boolean verifyPasswords(String encodedPassword, String rawPassword) {
        return argon2.verify(encodedPassword, rawPassword.toCharArray());
    }
}