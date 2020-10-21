package pabib.kata.fraction.cli;

import pabib.kata.fraction.core.Fraction;
import pabib.kata.fraction.formatter.FractionFormatter;
import pabib.kata.fraction.formatter.PrettyFractionFormatter;
import pabib.kata.fraction.formatter.SimpleFractionFormatter;
import pabib.kata.fraction.input.Input;
import pabib.kata.fraction.input.KeyboardInput;
import pabib.kata.fraction.output.ConsoleOutput;
import pabib.kata.fraction.output.Output;
import pabib.kata.fraction.repository.FractionEntity;
import pabib.kata.fraction.repository.FractionRepository;
import pabib.kata.fraction.repository.RedisFractionRepository;

import java.util.List;

import static pabib.kata.fraction.utilities.IntegerUtilities.isInt;

public class Application {
    public static void main(String[] args) {

        FractionFormatter formatter = new SimpleFractionFormatter();
        //FractionRepository fractionsRepository = InMemoryFractionRepository.createDefault();
        FractionRepository fractionsRepository = RedisFractionRepository.createDefault();
        Output output = new ConsoleOutput();
        Input input = new KeyboardInput();
        boolean falseBoolean = true;

        while (falseBoolean) {
            final List<FractionEntity> fractions = fractionsRepository.findAll();
            for (int i = 0; i < fractions.size(); i++)
                output.print(formatter.format(fractions.get(i)));

            output.print("\n(1)Add a fraction, (2)Delete a fraction, (3)Operations, (4)Change display mode, (q)Exit");

            String charInput = input.inputString((s) -> s.length() == 1
                    && (s.toCharArray()[0] >= '1'
                    && s.toCharArray()[0] <= '4')
                    || s.toCharArray()[0] == 'q');

            switch (charInput.toCharArray()[0]) {
                case '1':
                    addFractionCli(fractionsRepository, input, output);
                    break;
                case '2':
                    deleteFractionCli(fractionsRepository, input, output);
                    break;
                case '3':
                    fractionsRepository.add(chooseOperation(fractionsRepository, input, output));
                    break;
                case '4':
                    if (formatter instanceof SimpleFractionFormatter) {
                        formatter = new PrettyFractionFormatter();
                    } else {
                        formatter = new SimpleFractionFormatter();
                    }
                    break;
                case 'q':
                    System.exit(0);
                default:
                    falseBoolean = false;
            }
        }

    }

    public static void addFractionCli(FractionRepository fractionRepository, Input input, Output output) {
        output.print("enter the new fraction following this structure \"<numenator> / <denominator>\"");

        String stringInput = input.inputString((s) ->
                s.contains("/")
                        && s.split("/")[0].length() > 0
                        && isInt(s.split("/")[0])
                        && s.split("/")[1].length() > 0
                        && isInt(s.split("/")[1]));

        String[] stringNumber = stringInput.split("/");

        Fraction fraction = new Fraction(Integer.parseInt(stringNumber[0]), Integer.parseInt(stringNumber[1]));
        fractionRepository.add(fraction);
    }

    public static void deleteFractionCli(FractionRepository fractionRepository, Input input, Output output) {
        if (fractionRepository.isEmpty()) {
            output.print("No fraction to be deleted");
            return;
        }

        output.print("Select the fraction you want to delete : ");

        int intInput = input.inputInt((i) -> fractionRepository.remove(i));

        output.print("Fraction (" + (intInput) + ") has been deleted");
    }

    public static Fraction chooseOperation(FractionRepository fractionRepository, Input input, Output output) {
        if (fractionRepository.isEmpty()) {
            output.print("No fraction to be chosen");
            return null;
        }
        output.print("\n(1)Addition, (2)Subtraction, (3)Multiply, (4)Divide, (q)Cancel");


        String charInput = input.inputString((s) -> s.length() == 1
                && (s.toCharArray()[0] >= '1'
                && s.toCharArray()[0] <= '4')
                || s.toCharArray()[0] == 'q');


        if (charInput.toCharArray()[0] == 'q') return null;

        output.print("Select the first fraction");

        int idFirstFraction = input.inputInt((i) -> !fractionRepository.find(i).isEmpty());

        output.print("Select the second fraction");
        int idSecondFraction = input.inputInt((i) -> !fractionRepository.find(i).isEmpty());

        Fraction firstFraction = fractionRepository.find(idFirstFraction).get();

        Fraction secondFraction = fractionRepository.find(idSecondFraction).get();

        switch (charInput.toCharArray()[0]) {
            case '1':
                return firstFraction.addition(secondFraction);
            case '2':
                return firstFraction.subtract(secondFraction);
            case '3':
                return firstFraction.multiply(secondFraction);
            case '4':
                return firstFraction.divide(secondFraction);
            default:
        }
        return null;
    }
}