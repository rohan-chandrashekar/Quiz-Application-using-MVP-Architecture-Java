package main.java.Quizzy.view;

/**
 * Utility class for input validation.
 */
public class InputValidator {
    public static boolean isSingleDigitOption(String input, int min, int max) {
        if (input == null || input.length() != 1) return false;
        char c = input.charAt(0);
        return c >= ('0' + min) && c <= ('0' + max);
    }

    public static boolean isNonEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    // Add more validation methods as needed
} 