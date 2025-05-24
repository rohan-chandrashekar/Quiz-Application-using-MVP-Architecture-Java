package main.java.Quizzy.view;

/**
 * Enum for menu options to avoid magic strings/numbers.
 */
public enum MenuOptions {
    LOGIN('1'),
    REGISTER('2'),
    EXIT('3'),
    CHANGELOG('4');

    private final char code;
    MenuOptions(char code) { this.code = code; }
    public char getCode() { return code; }
} 