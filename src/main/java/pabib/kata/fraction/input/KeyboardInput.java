package pabib.kata.fraction.input;

import java.util.Scanner;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

import static pabib.kata.fraction.utilities.IntegerUtilities.isInt;

public class KeyboardInput implements Input {
    private final Scanner scan;

    public KeyboardInput() {
        scan = new Scanner(System.in);
    }

    @Override
    public int inputInt(IntPredicate predicate) {
        String input;
        do {
            input = scan.nextLine();
            while (!isInt(input)) {
                System.out.print("invalid input");
                input = scan.nextLine();
            }
        } while (!predicate.test(Integer.parseInt(input)));
        return Integer.parseInt(input);
    }

    @Override
    public String inputString(Predicate<String> predicate) {

        String input = scan.nextLine();

        while (!predicate.test(input)) {
            System.out.print("invalid input");
            input = scan.nextLine();
        }

        return input;
    }
}
