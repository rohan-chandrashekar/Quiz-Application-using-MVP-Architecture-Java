package main.java.Quizzy;

import main.java.Quizzy.view.CommandLineInterface;

public class MainApp extends Thread {
    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface();
        cli.run();
    }
}
