package pabib.kata.fraction.formatter;

import pabib.kata.fraction.repository.FractionEntity;

import static java.lang.Integer.max;

public class PrettyFractionFormatter implements FractionFormatter {

    @Override
    public String format(FractionEntity fraction) {
        int numeratorLength = String.valueOf(fraction.getNumerator()).length();
        int denominatorLength = String.valueOf(fraction.getDenominator()).length();

        return fraction.getId() + ")\n" +
                " ".repeat(max(0, denominatorLength - numeratorLength)) +
                fraction.getNumerator() +
                "\n" +
                "-".repeat(max(numeratorLength, denominatorLength)) +
                "\n" +
                " ".repeat(max(0, numeratorLength - denominatorLength)) +
                fraction.getDenominator() +
                "\n";
    }
}
