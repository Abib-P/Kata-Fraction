package pabib.kata.fraction.cli;

import pabib.kata.fraction.core.Fraction;
import pabib.kata.fraction.formatter.FractionFormatter;
import pabib.kata.fraction.formatter.PrettyFractionFormatter;
import pabib.kata.fraction.formatter.SimpleFractionFormatter;
import pabib.kata.fraction.repository.FractionRepository;
import pabib.kata.fraction.repository.InMemoryFractionRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        FractionFormatter formatter = new SimpleFractionFormatter();
        FractionRepository fractionsRepository = new InMemoryFractionRepository();
        Scanner scan = new Scanner(System.in);


        while (true) {
            final List<Fraction> fractions = fractionsRepository.findAll();
            for (int i = 0; i < fractions.size(); i++)
                System.out.println((i + 1) + ") " + formatter.format(fractions.get(i)));  //TODO Change format to show the n° of the actual fraction (because doesn't work with PrettyFractionFormatter)

            System.out.println("\n(1)Add a fraction, (2)Delete a fraction, (3)Operations, (4)Change display mode, (q)Exit");

            char input = scan.next().charAt(0);
            while ((input < '1' || '4' < input) && input != 'q') {
                System.out.print("invalid character");
                input = scan.next().charAt(0);
            }

            switch (input) {
                case '1':
                    addFractionCli(fractionsRepository, scan);
                    break;
                case '2':
                    deleteFractionCli(fractionsRepository, scan);
                    break;
                case '3':
                    fractionsRepository.add(chooseOperation(fractionsRepository, scan));
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
            }
        }

    }

    public static void addFractionCli(FractionRepository fractionRepository, Scanner scan) {
        System.out.println("enter the new fraction following this structure \"<numenator> / <denominator>\"");      //TODO loop when input is not valid
        String input = scan.next();                                                                                 //TODO program crash when input not valid!!

        String[] stringNumber = input.split("/");

        Fraction fraction = new Fraction(Integer.parseInt(stringNumber[0]), Integer.parseInt(stringNumber[1]));
        fractionRepository.add(fraction);
    }

    public static void deleteFractionCli(FractionRepository fractionRepository, Scanner scan) {
        if (fractionRepository.isEmpty()) {
            System.out.println("No fraction to be deleted");
            return;
        }

        System.out.println("Select the fraction you want to delete : ");                                                //TODO Create a cancel button to return to menu

        int input = scan.nextInt();
        input--;

        while (!fractionRepository.remove(input)) {
            System.out.print("invalid number");
            input = scan.nextInt();
            input--;
        }

        System.out.println("Fraction (" + (input + 1) + ") has been deleted");
    }

    public static Fraction chooseOperation(FractionRepository fractionRepository, Scanner scan) {
        System.out.println("\n(1)Addition, (2)Subtraction, (3)Multiply, (4)Divide");                                    //TODO Create a cancel button to return to menu

        int input = scan.nextInt();
        while (input < 1 || 4 < input) {
            System.out.print("invalid number");
            input = scan.nextInt();
        }

        System.out.println("Select the first fraction");
        Optional<Fraction> firstFraction = fractionRepository.find(scan.nextInt()-1);

        System.out.println("Select the second fraction");
        Optional<Fraction> secondFraction = fractionRepository.find(scan.nextInt()-1);

        if (firstFraction.isPresent() && secondFraction.isPresent()) {
            switch (input) {
                case 1:
                    return firstFraction.get().addition(secondFraction.get());
                case 2:
                    return firstFraction.get().subtract(secondFraction.get());
                case 3:
                    return firstFraction.get().multiply(secondFraction.get());
                case 4:
                    return firstFraction.get().divide(secondFraction.get());
                default:
            }
        }
        return null;
    }
}