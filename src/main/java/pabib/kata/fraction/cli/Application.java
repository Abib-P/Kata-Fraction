package pabib.kata.fraction.cli;

import pabib.kata.fraction.core.Fraction;
import pabib.kata.fraction.formatter.FractionFormatter;
import pabib.kata.fraction.formatter.PrettyFractionFormatter;
import pabib.kata.fraction.formatter.SimpleFractionFormatter;
import pabib.kata.fraction.repository.FractionRepository;
import pabib.kata.fraction.repository.InMemoryFractionRepository;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        FractionFormatter formatter = new SimpleFractionFormatter();
        FractionRepository fractionsRepository = new InMemoryFractionRepository();

        try {

        }catch (Exception e)
        {

        }

        while(true)
        {
                final var fractions = fractionsRepository.findAll();
            for(int i = 0; i < fractions.size(); i++)
                System.out.println( (i+1) + ") " + formatter.format(fractions.get(i)));  //TODO Change format to show the n° of the actual fraction (because doesn't work with PrettyFractionFormatter)
            }

            System.out.println("\n(1)Add a fraction, (2)Delete a fraction, (3)Operations, (4)Change display mode, (q)Exit ");

            Scanner scan = new Scanner(System.in);
            char input = scan.next().charAt(0);
            while( (input < '1' || '4' < input ) && input != 'q')
            {
                System.out.print("invalid character");
                input = scan.next().charAt(0);
            }

            switch (input)
            {
                case '1':
                    addFractionCli(fractionsRepository, scan);
                    break;
                case '2':
                    deleteFractionCli(fractionsRepository, scan);
                    break;
                case '3':
                    fractions.add(chooseOperation(fractions, scan));
                    break;
                case '4':
                    if(formatter.getClass().getSimpleName().equals("SimpleFractionFormatter")) {
                        formatter = new PrettyFractionFormatter();
                    }
                    else{
                        formatter = new SimpleFractionFormatter();
                    }

                    break;
                case 'q':
                    System.exit(0);
            }
        }
    }

    public static void addFractionCli(FractionRepository fractionRepository, Scanner scan)
    {
        System.out.println("enter the new fraction following this structure \"<numenator> / <denominator>\"");      //TODO loop when input is not valid
        String input = scan.next();                                                                                 //TODO program crash when input not valid!!

        String[] stringNumber = input.split("/");

        Fraction fraction = new Fraction(Integer.parseInt(stringNumber[0]), Integer.parseInt(stringNumber[1]));
        fractionRepository.add(fraction);
    }

    public static void deleteFractionCli(FractionRepository fractionRepository, Scanner scan)
    {
        if (fractionRepository.findAll().isEmpty())
        {
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

        System.out.println("Fraction (" + (input+1) + ") has been deleted");
    }

    public static Fraction chooseOperation(FractionRepository fractionRepository, Scanner scan)
    {
        System.out.println("\n(1)Addition, (2)Subtraction, (3)Multiply, (4)Divide");                                    //TODO Create a cancel button to return to menu

        int input = scan.nextInt();
        while( input < 1 || 4 < input)
        {
            System.out.print("invalid number");
            input = scan.nextInt();
        }

        System.out.println("Select the first fraction");
        int firstFraction = scan.nextInt();

        firstFraction--;
        while( firstFraction < 0 || fractionRepository.findAll().size() <= firstFraction)
        {
            System.out.print("invalid number");
            firstFraction = scan.nextInt();
            firstFraction--;
        }

        System.out.println("Select the second fraction");
        int secondFraction = scan.nextInt();

        secondFraction--;
        while( secondFraction < 0 || fractions.size() <= secondFraction)
        {
            System.out.print("invalid number");
            secondFraction = scan.nextInt();
            secondFraction--;
        }

        switch (input) {
            case 1 :
                return fractions.get(firstFraction).addition(fractions.get(secondFraction));
            case 2 :
                return fractions.get(firstFraction).subtract(fractions.get(secondFraction));
            case 3 :
                return fractions.get(firstFraction).multiply(fractions.get(secondFraction));
            default:
                return fractions.get(firstFraction).divide(fractions.get(secondFraction));
        }
    }
}