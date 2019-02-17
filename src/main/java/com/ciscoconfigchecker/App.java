package com.ciscoconfigchecker;

public class App {
    public static void main(String[] args) {
        // Dirty solution to fix: Error: JavaFX runtime components are missing, and are required to run this application
        // Make a main method that does not extend Application. com.ciscoconfigchecker.App is defined as the mainClass in the pom.xml
        Main.main(args);
    }
}
