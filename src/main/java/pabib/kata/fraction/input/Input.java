package pabib.kata.fraction.input;

import java.util.function.Predicate;

public interface Input {
    default String inputString() {
        return inputString(input -> true);
    }

    int inputInt(Predicate<Integer> predicate);

    String inputString(Predicate<String> predicate);
}
