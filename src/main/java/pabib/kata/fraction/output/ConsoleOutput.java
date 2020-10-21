package pabib.kata.fraction.output;

public class ConsoleOutput implements Output {
    @Override
    public void print(String message, Object... args) {
        System.out.printf(message + "%n", args);
    }
}
