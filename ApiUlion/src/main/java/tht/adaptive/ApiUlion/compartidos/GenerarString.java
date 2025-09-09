package tht.adaptive.ApiUlion.compartidos;

import java.security.SecureRandom;

public final class GenerarString {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private GenerarString() {
        // Private constructor to prevent instantiation
    }

    /**
     * Generates a random string of a specified length.
     *
     * @param length The length of the string to generate.
     * @return A randomly generated string.
     */
    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
